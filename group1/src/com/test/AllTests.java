package com.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestFKValidation.class, TestPKValidation.class, TestPopulateDatabase.class, TestQueries.class})
public class AllTests {

}
