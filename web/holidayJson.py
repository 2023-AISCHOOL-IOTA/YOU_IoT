import csv
from datetime import date
from flask import Flask, jsonify, render_template
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
def get_data():
        sql = "select * from holiday"
        cursor.execute(sql)
        data = cursor.fetchall()
        cursor.close()
#        return render_template("i.html", data=dict(data))
        return dict(data)

if __name__ == "__main__":
    app.run(host="0.0.0.0",port=8080, debug=True)

