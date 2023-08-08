# web 페이지에 주차장 보여주기
from flask import Flask, jsonify, render_template
import mysql.connector

app = Flask(__name__)

# MySQL 연결 설정
app.config["MYSQL_HOST"] = "project-db-stu3.smhrd.com"  # MySQL 호스트 주소
app.config["MYSQL_USER"] = "Insa4_IOTA_hacksim_3"  # MySQL 사용자 이름
app.config["MYSQL_PASSWORD"] = "aishcool3"  # MySQL 비밀번호
app.config["MYSQL_DB"] = "Insa4_IOTA_hacksim_3"  # 사용할 데이터베이스 이름

# MySQL 연결 객체 생성
mysql_conn = mysql.connector.connect(
    host=app.config["MYSQL_HOST"],
    user=app.config["MYSQL_USER"],
    password=app.config["MYSQL_PASSWORD"],
    database=app.config["MYSQL_DB"],
    port = 3307
)

# 라우트 정의: 데이터베이스에서 값을 가져와 JSON 응답으로 내보냅니다.
@app.route("/", methods=["GET"])
def get_data():
    # 커서 객체 생성
    cursor = mysql_conn.cursor()

    # 예시로 모든 레코드를 조회하고 결과를 리스트로 만들어 JSON으로 반환합니다.
    query = "SELECT * FROM street_park;"  # your_table_name에는 사용할 테이블 이름을 넣으세요.
    cursor.execute(query)
    
    # 커서와 연결 닫기
    # cursor.close()
    street_data = cursor.fetchall()
    num1 = street_data[0]
    cursor.close()

    # index.html 템플릿 렌더링하며 데이터 전달
    return render_template("CSS_street_park.html", data=street_data, num1 = num1)

if __name__ == "__main__":
    app.run(host = "0.0.0.0", port = 8080, debug=True)