set term pdf
set key top right spacing 1.2
set key font ",18"
set xtics font ",18" 
set ytics font ",18" 
set xlabel font ",18" 
set ylabel font ",18" 
set bmargin 5
set lmargin 10
set rmargin 8
set ylabel "Number of Transitions" offset -2,0,0
set xlabel "Execution Time (sec)"
set grid x y
set xrange [60:600000]
set yrange [120:400]
set log x
set xtics ("60" 60,"600" 600,"6000" 6000,"60000" 60000,"600000" 600000)


plot for [IDX=0:4] 'Figure 9.data' i IDX u 1:2 w linespoints ps .5 title columnheader(1),\
     for [IDX=5:5] 'Figure 9.data' i IDX u 1:2 w circles  lc rgb "gray" notitle,\
     for [IDX=6:6] 'Figure 9.data' i IDX u 1:2 pt 8 ps 2  lc rgb "green" notitle