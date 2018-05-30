import MySQLdb
import random
import array 
#open db connection
db = MySQLdb.connect("localhost","root","","timetabler")

#prepare a cursor object
cursor = db.cursor()

#prepare sql query to insert a record into the database 
sql="delete from subject_student ";

try:
	#execute the command
	cursor.execute(sql)
	
	i=0
	while (i<7):
		i+=1
		j=0
		mylist = [0]*100
		#mylist = array.array('i')
		while (j<100):
			
			a = random.randint(1,800)
			while(a in mylist):
				a=random.randint(1,800)
			mylist[j]=a
			#mylist.add(a)
			indexnumber=("IM/2013/")+str(a)
			sql = "INSERT INTO `subject_student`(`subject_id`, `student_id`) VALUES ('%d','%s')" \
			% (i,indexnumber)
			cursor.execute(sql)
			j+=1
	
	#commit your changes in the database
	db.commit()
	print("Done")
except Exception as Argument:
	#rollback changes
	db.rollback()
	print("Error",Argument)
	
#dissconnect from the server
db.close()