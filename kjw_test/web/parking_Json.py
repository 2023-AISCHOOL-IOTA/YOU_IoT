import csv
from datetime import date
from flask import Flask, jsonify, render_template, Response
import json

# CSV 파일 경로 지정

app = Flask(__name__)
import pymysql as ps

db = ps.connect(
    host="project-db-stu3.smhrd.com", user="Insa4_IOTA_hacksim_3", passwd="aishcool3", db="Insa4_IOTA_hacksim_3", charset="utf8mb4",
    port = 3307
)

# 커서 생성
cursor = db.cursor()
cursor.execute("use Insa4_IOTA_hacksim_3")

@app.route("/", methods=["GET"])
def get_data() :
    sql = "select * from parking"
    cursor.execute(sql)

    parking_list = []
    columns = [column[0] for column in cursor.description]
    for row in cursor.fetchall():
        row_data = dict(zip(columns, row))
        parking_id = row_data.pop('parkingID')  # parkingID를 key로 사용하므로 따로 빼줌
        parking_list.append({parking_id: row_data})
    # 커서 닫기
    cursor.close()
    
    # JSON 출력
    return Response(json.dumps(parking_list, ensure_ascii=False), content_type="application/json; charset=utf-8",)



if __name__ == "__main__":
    app.run(host="0.0.0.0",port=8080, debug=True)

