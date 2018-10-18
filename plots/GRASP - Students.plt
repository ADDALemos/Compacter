set term pdf
set key font ",18"
set xtics font ",18" 
set ytics font ",18" 
set xlabel font ",18" 
set ylabel font ",18" 
set grid x y
set ylabel "Y Label" offset -3,0,0
set bmargin 5
set lmargin at screen 0.15
# Key means label...
set key top left
set ylabel "Number of Students"
set xlabel "Number of Iterations"
set grid x y
plot  "data.txt" using 1:2 title 'Alameda 1^{st}' with lines, "data.txt" using 1:3 title 'Alameda 2^{nd}' with lines
