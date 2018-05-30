import MySQLdb

#open db connection
db = MySQLdb.connect("localhost","root","","timetabler")

#prepare a cursor object
cursor = db.cursor()

#prepare sql query to insert a record into the database 
sql="delete from student ";

try:
	#execute the command
	cursor.execute(sql)
	
	i=0
	while (i<800):
		i+=1
		indexnumber=("IM/2013/")+str(i)
		username=("student")+str(i)
		password="123456"
		name=("Student")+str(i)
		degree=("MIT")
		joinedYear= ("2014")
		print("here")
		sql = "INSERT INTO student(index_number,username,password,name,degree,joined_year)\
		VALUES ('%s','%s','%s','%s','%s','%s')" % (indexnumber,username,password,name,degree,joinedYear)
		cursor.execute(sql)
	
	#commit your changes in the database
	db.commit()
	print("Done")
except Exception as Argument:
	#rollback changes
	db.rollback()
	print("Error",Argument)
	
#dissconnect from the server
db.close()