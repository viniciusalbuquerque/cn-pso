# attach(GlobalFitnessSphere.txt)
# fpe = read.table("GlobalFitnessSphere.txt")
# library(ggplot2)
# Box plot 
fpe = read.table("GlobalFitnessRosenbrock.txt", col.names=c("fitness"))
iteration = array(1:10000)
# fpe
attach(fpe)
bp <- ggplot(fpe, aes(x=dose, y=len)) + geom_boxplot()
bp
# plot(iteration,fitness)
# boxplot(fitness)
# abline(lm(fpe~x))
# plot(wt, mpg) 
# abline(lm(mpg~wt))
# title("Fitness variation to fixed W")