package test.main.exceptions; 

import main.exceptions.BadPuzzleException;
import main.exceptions.MultipleSolutionException;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.assertEquals;

/** 
* MultipleSolutionException Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 19, 2018</pre> 
* @version 1.0 
*/ 
public class MultipleSolutionExceptionTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getOriginalPuzzle() 
* 
*/ 
@Test
public void testGetOriginalPuzzle() throws Exception {
	MultipleSolutionException ex = new MultipleSolutionException("Bad Puzzle", "Test Message");
	assertEquals("Test Message", ex.getOriginalPuzzle());

	ex = new MultipleSolutionException("Bad Puzzle", "Another Test Message");
	assertEquals("Another Test Message", ex.getOriginalPuzzle());
}

} 
