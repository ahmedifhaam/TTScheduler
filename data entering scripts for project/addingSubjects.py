import MySQLdb
import random
#open db connection
db = MySQLdb.connect("localhost","root","","timetabler")

#prepare a cursor object
cursor = db.cursor()

#prepare sql query to insert a record into the database 
sql="delete from subjects";

try:
    
    #execute the command
    cursor.execute(sql)

    totalNumberofModules=6 #give the count of subjects want to add
    #because we dont have referencial integrity make sure you dont give the number greater
    #than the modules available

    #loop through the subjects mentioned in the list
    j=1
    while(totalNumberofModules>=j):
        sql = "INSERT INTO `subjects` (`_id`,`module_id`,`year`,`offered_year`,`offered_semester`,`is_repeat`)"\
              "VALUES ('%d','%d','%s','%s','%s','%d')"\
              % (j,j,'2013','3','1',0)
        cursor.execute(sql)
        j+=1

    db.commit()
    print("Done")
except Exception as Argument:
    #rollback changes
    db.rollback()
    print("Error",Argument)

#dissconnect from the server
db.close()
