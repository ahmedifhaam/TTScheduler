import MySQLdb
import random
#open db connection
db = MySQLdb.connect("localhost","root","","timetabler")

#prepare a cursor object
cursor = db.cursor()

#prepare sql query to insert a record into the database 
sql="delete from modules ";

try:
    
    #execute the command
    cursor.execute(sql)

    #declare available course modules in an arraylist
    mylist = [
        "INTE 31512","INTE 31532","INTE 31522","INTE 31533","INTE 31553",
        "INTE 31582"]

    i=0 #will be used to mention _id value

    #loop through the subjects mentioned in the list
    for subject in mylist:
        i+=1 #should start from one thats why increment added as first line inside the loop 
        subjectname = subject+str(" subject")
        sql = "INSERT INTO `modules`(`_id`, `code`,`name`) VALUES ('%d','%s','%s')" \
        % (i,subject,subjectname)
        print(sql)
        cursor.execute(sql)

        

    db.commit()
    print("Done")
except Exception as Argument:
    #rollback changes
    db.rollback()
    print("Error",Argument)

#dissconnect from the server
db.close()
