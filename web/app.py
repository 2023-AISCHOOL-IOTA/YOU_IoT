# web 페이지에 주차장 보여주기
from flask import Flask, jsonify, render_template
import mysql.connector

app = Flask(__name__)

# MySQL 연결 설정
app.config["MYSQL_HOST"] = "localhost"  # MySQL 호스트 주소
app.config["MYSQL_USER"] = "root"  # MySQL 사용자 이름
app.config["MYSQL_PASSWORD"] = "사용자 비밀번호"  # MySQL 비밀번호
app.config["MYSQL_DB"] = "사용자 데이터베이스 이름"  # 사용할 데이터베이스 이름

# MySQL 연결 객체 생성
mysql_conn = mysql.connector.connect(
    host=app.config["MYSQL_HOST"],
    user=app.config["MYSQL_USER"],
    password=app.config["MYSQL_PASSWORD"],
    database=app.config["MYSQL_DB"],
)


# 라우트 정의: 데이터베이스에서 값을 가져와 JSON 응답으로 내보냅니다.
@app.route("/", methods=["GET"])
def get_data():
    # 커서 객체 생성
    cursor = mysql_conn.cursor()

    # 예시로 모든 레코드를 조회하고 결과를 리스트로 만들어 JSON으로 반환합니다.
    query = "SELECT * FROM parking;"  # your_table_name에는 사용할 테이블 이름을 넣으세요.
    cursor.execute(query)

    # 쿼리 결과 가져오기
    # result = []
    # for row in cursor.fetchall():
    #     parkingId = int(row[0])
    #     address = row[1]
    #     spaces = int(row[2])
    #     classes = row[3]
    #     parkName = row[4]
    #     parkType = row[5]

    # 커서와 연결 닫기
    # cursor.close()
    data = cursor.fetchall()
    num1 = data[0]
    cursor.close()

    # index.html 템플릿 렌더링하며 데이터 전달
    return render_template("index.html", data=data, num1=num1)
    # return render_template("index.html", data=data)

    # return render_template("index.html")


if __name__ == "__main__":
    app.run(debug=True)