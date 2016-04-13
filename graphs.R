# attach(GlobalFitnessSphere.txt)
fpe = read.table("GlobalFitnessSphere.txt")
fpe = read.table("GlobalFitnessSphere.txt", col.names=c("fitness"))
iteration = array(1:10000)
fpe
attach(fpe)

plot(iteration,fitness)
# abline(lm(fpe~x))
# plot(wt, mpg) 
# abline(lm(mpg~wt))
title("Regression of MPG on Weight")