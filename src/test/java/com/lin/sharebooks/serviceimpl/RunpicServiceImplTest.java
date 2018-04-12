package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.service.RunpicService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * RunpicServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>04/12/2018</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RunpicServiceImplTest {
    @Autowired
    private RunpicService runpicService;
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: update(Runpic runpic)
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println(runpicService.findAll());
    }

    /**
     * Method: getByRid(int rid)
     */
    @Test
    public void testGetByRid() throws Exception {
        System.out.println(runpicService.getByRid(1));
    }


} 
