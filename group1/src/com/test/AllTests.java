package com.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestPopulateDatabase.class, TestQueries.class, TestFKValidation.class, TestFKValidation.class })
public class AllTests {

}
