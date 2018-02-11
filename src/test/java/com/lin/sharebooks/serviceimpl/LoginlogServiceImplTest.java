package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.service.LoginlogService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * LoginlogServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/06/2018</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginlogServiceImplTest {
    @Autowired
    private LoginlogService loginlogService;
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: addLoginlog(Loginlog loginlog)
     */
    @Test
    public void testAddLoginlog() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateLoginlog(Loginlog loginlog)
     */
    @Test
    public void testUpdateLoginlog() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getByUserid(int userid)
     */
    @Test
    public void testGetByUserid() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: find2kmLoginlogs(double longitude, double latitude)
     */
    @Test
    public void testFind2kmLoginlogs() throws Exception {
//TODO: Test goes here...
        System.out.println(loginlogService.find2kmLoginlogs(118.79189,32.060255));
    }


} 
