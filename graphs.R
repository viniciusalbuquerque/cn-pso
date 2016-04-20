fpe = read.table("LocalFitnessRR.txt", col.names=c("fitness"))
iteration = array(1:10000)
# fpe
attach(fpe)

plot(iteration,fitness)
# boxplot(fitness)
# abline(lm(fpe~x))
# plot(wt, mpg) 
# abline(lm(mpg~wt))
# title("Fitness variation to fixed W")