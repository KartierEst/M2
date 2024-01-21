package fr.uge.jee.aop.students;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.StringJoiner;

@Aspect
@Component
public class MyAspect {

    private final List<Long> saveDb = new ArrayList<>();
    private final List<Long> loadDb = new ArrayList<>();



/*    @Before("execution(* fr.uge.jee.aop.students.RegistrationService.create*(..))")
    public void beforeAdd(JoinPoint jp) throws Throwable
    {
        System.out.println("Before create");
    }*/

    @Before("execution(* fr.uge.jee.aop.students.RegistrationService.create*(..))")
    public void beforeAdd(JoinPoint jp) throws Throwable
    {
        var builder = new StringJoiner(",","[","]");
        Object[] args = jp.getArgs();
        for(var i : args){
            builder.add(i.toString());
        }
        System.out.println("Calling " + jp.getSignature().getName() + " with arguments " + builder.toString());
    }

/*    @After("execution(* fr.uge.jee.aop.students.RegistrationService.create*(..))")
    public void afterAdd(JoinPoint jp) throws Throwable
    {
        System.out.println("After create");
    }*/

    @AfterReturning(value = "execution(* fr.uge.jee.aop.students.RegistrationService.create*(..))" , returning = "id")
    public void afterAdd(JoinPoint jp, Object id) throws Throwable
    {
        System.out.println("Return id " + id + " by " + jp.getSignature().getName());
    }


    @Around(value = "execution(* fr.uge.jee.aop.students.RegistrationService.*DB(..))")
    public Object saveDb(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        if (joinPoint.getSignature().getName().equals("loadFromDB")) {
            loadDb.add(executionTime);
        } else {
            saveDb.add(executionTime);
        }
        return result;
    }

    public String stringSave(List<Long> list){
        var joiner = new StringJoiner(",","[","]");
        for(var time : list){
            joiner.add(time.toString());
        }
        return joiner.toString();
    }

    public OptionalDouble average(List<Long> list){
        return list.stream().mapToLong(Long::longValue).average();
    }


    public String stringTimer(){
        var avgSave = average(saveDb);
        var avgLoad = average(loadDb);
        var builder = new StringBuilder();
        builder.append("\nDB timing report:\n\tsaveToDB\n")
                .append("\t\tMesured access times : ")
                .append(stringSave(saveDb))
                .append("\n")
                .append("\t\tAverage time : ")
                .append(avgSave.isPresent() ? avgSave.getAsDouble() : null)
                .append("\n");

        builder.append("\tloadFromDB\n")
                .append("\t\tMesured access times : ")
                .append(stringSave(loadDb))
                .append("\n")
                .append("\t\tAverage time : ")
                .append(avgLoad.isPresent() ? avgLoad.getAsDouble() : null)
                .append("\n");

        return builder.toString();
    }
}