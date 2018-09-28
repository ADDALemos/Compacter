Two java approaches to solve the allocation of lectures to rooms are available in this repository. The solution maximizes the number of students seated and minimizes the number of gaps in a room's timetable.

The first approach is a greedy algorithm, with a cost function which is proved to be monotone, positive and sub-modular.
The second approach uses the IBM's CPLEX solver to solve the problem in java.

NEW: A GRASP: GREEDY RANDOMIZED ADAPTIVE SEARCH PROCEDURES where Local search based on neighborhoods (lectures assigned to rooms with the same capacity).

__Course Timetabling Data Sets__ The data sets consists on the data from the timetables of Instituto Superior Tecnico of the academic year of 2016/2017. This data sets are separated in two semesters and in two campi (Alameda and Taguspark). The data sets for Alameda are considerably larger.

These data sets have three types of files containing the data for the classrooms, timetables and curricula of each IST campus (Alameda or Taguspark).

__Space__

The space files contain the data about the classrooms: _Name, capacity, id and type (hall/normal/Lab)_ The files containing the word _Lab_ on the file name contain only Laboratories, otherwise the file do not contain any Laboratories.

__Timetable__ 

The timetables file contains the data about the scheduled lectures: _Acronym, starting hour, starting minutes, ending hour, ending minutes, day of the week, number of students enrolled, type (theoretical, problems), room id and room name_

Consider, for example, a theoretical class of Algebra course that is lectured on Monday from 9:30 to 10:00. This class has 45 students enrolled and it is taught in the FA1 hall. The timetable file would have the following line AL, 9, 30, 10, 0, 2, 45, theoretical, 1, FA1
