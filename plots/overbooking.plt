set term pdf
set key font ",18"
set xtics font ",18" 
set ytics font ",18" 
set xlabel font ",18" 
set ylabel font ",18" 
set bmargin 5

set key top left
set xlabel "Percentage of students above the ideal capacity"
set ylabel "Number of Time Slots"
set grid x y
#set yrange [0:900]
set xrange [0:70]
set yrange [0:70]
#set log x
#set xtics ("60" 60,"600" 600,"6000" 6000,"60000" 60000,"600000" 600000)


plot "Figure 7 - Overbooking Taguspark 2st.data" \
      using 1:2 \
      with points\
      title "Hand-made",\
"Figure 7 - Overbooking Taguspark 2st.data" \
      using 1:3 \
      with points\
      title "ILP",\
"Figure 7 - Overbooking Taguspark 2st.data" \
      using 1:4 \
      with points\
      title "Greedy"