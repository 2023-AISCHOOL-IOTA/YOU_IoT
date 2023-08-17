# web 페이지에 주차장 보여주기
from flask import Flask, jsonify, render_template, Response
import mysql.connector, json


app = Flask(__name__)

app.config["MYSQL_HOST"] = "project-db-stu3.smhrd.com"  # MySQL 호스트 주소
app.config["MYSQL_USER"] = "Insa4_IOTA_hacksim_3"  # MySQL 사용자 이름
app.config["MYSQL_PASSWORD"] = "aishcool3"  # MySQL 비밀번호
app.config["MYSQL_DB"] = "Insa4_IOTA_hacksim_3"  # 사용할 데이터베이스 이름

mysql_conn = mysql.connector.connect(
    host=app.config["MYSQL_HOST"],
    user=app.config["MYSQL_USER"],
    password=app.config["MYSQL_PASSWORD"],
    database=app.config["MYSQL_DB"],
    port=3307,
)

# 커서 객체 생성
cursor = mysql_conn.cursor()

# 원하는 데이터 갯수만큼 보이게 하는 변수
# per_page = 20

# 라우트 정의: 데이터베이스에서 값을 가져와 JSON 응답으로 내보냅니다.
@app.route("/", methods=["GET"])
def home():
    return render_template("parking_website.html")


##### web_table_parking
@app.route("/parking", methods=["GET"])
def parking():
    # 커서 객체 생성
    cursor = mysql_conn.cursor()

    # 예시로 모든 레코드를 조회하고 결과를 리스트로 만들어 JSON으로 반환합니다.
    query = "SELECT * FROM parking;"  # your_table_name에는 사용할 테이블 이름을 넣으세요.
    cursor.execute(query)

    data = cursor.fetchall()
    cursor.close()

    # index.html 템플릿 렌더링하며 데이터 전달
    return render_template("CSS_parking.html", data=data)


##### web_table_holiday
@app.route("/holiday", methods=["GET"])
def holiday():
    cursor = mysql_conn.cursor()

    query = "SELECT * FROM holiday;"
    cursor.execute(query)

    # 커서와 연결 닫기
    data = cursor.fetchall()
    cursor.close()

    return render_template("CSS_holiday.html", data=data)


##### web_table_street_park
@app.route("/street_park", methods=["GET"])
def street_park():
    cursor = mysql_conn.cursor()

    query = "SELECT * FROM street_park;"
    cursor.execute(query)

    street_data = cursor.fetchall()
    cursor.close()

    return render_template("CSS_street_park.html", data=street_data)


##### parkingJson
@app.route("/parkingJson", methods=["GET"])
def get_parkingDataJson():
    cursor = mysql_conn.cursor()
    sql = "select * from parking"
    cursor.execute(sql)

    data2 = []
    columns = [column[0] for column in cursor.description]
    for row in cursor.fetchall():
        row_data = dict(zip(columns, row))
        parking_id = row_data.pop("parkingID")  # parkingID를 key로 사용하므로 따로 빼줌
        data2.append({parking_id: row_data})
    # 커서 닫기
    cursor.close()

    # JSON 출력
    return Response(
        json.dumps(data2, ensure_ascii=False),
        content_type="application/json; charset=utf-8",
    )


##### holidayJson
@app.route("/holidayJson", methods=["GET"])
def get_holidayDataJson():
    cursor = mysql_conn.cursor()
    sql = "select * from holiday"
    cursor.execute(sql)
    data = cursor.fetchall()
    cursor.close()
    return dict(data)


##### street_ParkJson
@app.route("/street_parkJson", methods=["GET"])
def get_street_parkDataJson():
    cursor = mysql_conn.cursor()
    sql = "select * from street_park"

    sql = "select * from street_park"
    cursor.execute(sql)

    data_street = []
    columns = [column[0] for column in cursor.description]
    for row in cursor.fetchall():
        row_data = dict(zip(columns, row))
        street_id = row_data.pop("street_id")  # parkingID를 key로 사용하므로 따로 빼줌
        data_street.append({street_id: row_data})
    # 커서 닫기
    cursor.close()

    # JSON 출력
    return Response(
        json.dumps(data_street, ensure_ascii=False),
        content_type="application/json; charset=utf-8",
    )


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8050, debug=True)