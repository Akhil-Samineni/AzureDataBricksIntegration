package com.samiak.azuredatabricks.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.samiak.azuredatabricks.model.Person;

@DgsComponent
public class PersonResolver {

    @DgsQuery
    public String hello(){
        return "Hello from graphql";
    }

    @DgsMutation
    public Person createPerson(@InputArgument("name") String name,@InputArgument("age") int age){
        return new Person();
    }
}
