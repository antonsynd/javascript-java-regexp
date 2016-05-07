package org.ecma.ecmascript.test.builtins;

import static org.junit.Assert.*;

import org.ecma.ecmascript.builtins.*;
import org.junit.Test;

public class RegExpTest
{
	private final String lipsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent ultrices nisi tincidunt dignissim ultrices. Fusce eget quam dictum justo condimentum accumsan. Integer sodales dictum ex, quis euismod lacus facilisis vel. Nulla non fermentum massa. Pellentesque congue ipsum orci, vitae porta sapien eleifend a. Quisque tincidunt, lectus eget convallis fringilla, dui massa laoreet lorem, sed imperdiet ex erat mattis mauris. In venenatis turpis sit amet hendrerit blandit. Nunc sit amet eleifend purus. In hac habitasse platea dictumst. Nulla lacus massa, consectetur id maximus vel, sodales laoreet augue. Aliquam quis sapien quis urna sodales sollicitudin quis eget sem. Aenean auctor, risus sit amet rutrum elementum, dui felis condimentum neque, et scelerisque magna risus id quam.";
	
	private RegExp r;
	private MatchResult m;
	
	@Test
	public void testLength()
	{
		r = new RegExp("");
		assertEquals(r.length(), 2);
		
		r = new RegExp("\\((\\d{3,3})\\)(?: |-)?\\d{3,3}(?: |-)?\\d{4,4}", "g");
		assertEquals(r.length(), 2);
	}
	
	@Test
	public void testFlags()
	{
		String flags = "";
		r = new RegExp("");
		assertEquals(false, r.dotall());
		assertEquals(false, r.global());
		assertEquals(false, r.ignoreCase());
		assertEquals(false, r.multiline());
		assertEquals(flags, r.flags());
		
		r = new RegExp("", flags);
		assertEquals(false, r.dotall());
		assertEquals(false, r.global());
		assertEquals(false, r.ignoreCase());
		assertEquals(false, r.multiline());
		assertEquals(flags, r.flags());
		
		flags = "g";
		r = new RegExp("", flags);
		assertEquals(false, r.dotall());
		assertEquals(true, r.global());
		assertEquals(false, r.ignoreCase());
		assertEquals(false, r.multiline());
		assertEquals(flags, r.flags());
		
		flags = "i";
		r = new RegExp("", flags);
		assertEquals(false, r.dotall());
		assertEquals(false, r.global());
		assertEquals(true, r.ignoreCase());
		assertEquals(false, r.multiline());
		assertEquals(flags, r.flags());
		
		flags = "m";
		r = new RegExp("", flags);
		assertEquals(false, r.dotall());
		assertEquals(false, r.global());
		assertEquals(false, r.ignoreCase());
		assertEquals(true, r.multiline());
		assertEquals(flags, r.flags());
		
		flags = "s";
		r = new RegExp("", flags);
		assertEquals(true, r.dotall());
		assertEquals(false, r.global());
		assertEquals(false, r.ignoreCase());
		assertEquals(false, r.multiline());
		assertEquals(flags, r.flags());
		
		flags = "gi";
		r = new RegExp("", flags);
		assertEquals(false, r.dotall());
		assertEquals(true, r.global());
		assertEquals(true, r.ignoreCase());
		assertEquals(false, r.multiline());
		assertEquals(flags.length(), r.flags().length());
		assertNotEquals(-1, r.flags().indexOf("g"));
		assertNotEquals(-1, r.flags().indexOf("i"));
		
		flags = "gm";
		r = new RegExp("", flags);
		assertEquals(false, r.dotall());
		assertEquals(true, r.global());
		assertEquals(false, r.ignoreCase());
		assertEquals(true, r.multiline());
		assertEquals(flags.length(), r.flags().length());
		assertNotEquals(-1, r.flags().indexOf("g"));
		assertNotEquals(-1, r.flags().indexOf("m"));
		
		flags = "gism";
		r = new RegExp("", flags);
		assertEquals(true, r.dotall());
		assertEquals(true, r.global());
		assertEquals(true, r.ignoreCase());
		assertEquals(true, r.multiline());
		assertEquals(flags.length(), r.flags().length());
		assertNotEquals(-1, r.flags().indexOf("g"));
		assertNotEquals(-1, r.flags().indexOf("i"));
		assertNotEquals(-1, r.flags().indexOf("s"));
		assertNotEquals(-1, r.flags().indexOf("m"));
	}
	
	@Test
	public void testLastIndex()
	{
		r = new RegExp("it");
		assertEquals(r.lastIndex(), 0);
		
		// Global flag for test
		String searchStr = "it";
		r = new RegExp(searchStr, "g");
		
		int lastIndex = 0;
		while (lastIndex != -1)
		{
			lastIndex = lipsum.indexOf(searchStr, lastIndex);
			
			if (lastIndex != -1)
			{
				lastIndex += searchStr.length();
				
				// Found one "it", assert that the regex's lastIndex matches
				r.test(lipsum);
				assertEquals(lastIndex, r.lastIndex());
			}
		}
		
		// Global flag for test
		r = new RegExp(searchStr, "g");
		
		lastIndex = 0;
		while (lastIndex != -1)
		{
			lastIndex = lipsum.indexOf(searchStr, lastIndex);
			
			if (lastIndex != -1)
			{
				lastIndex += searchStr.length();
				
				// Found one "it", assert that the regex's lastIndex matches
				r.exec(lipsum);
				assertEquals(lastIndex, r.lastIndex());
			}
		}
	}
	
	@Test
	public void testSetLastIndex()
	{
		
	}
	
	@Test
	public void testTest()
	{
		// Sanity check
		r = new RegExp("it");
		assertEquals(true, r.test(lipsum));
		assertEquals(false, r.test("hello"));
		
		// Global flag for test
		String searchStr = "it";
		r = new RegExp(searchStr, "g");
		
		int lastIndex = 0;
		while (lastIndex != -1)
		{
			lastIndex = lipsum.indexOf(searchStr, lastIndex);
			
			if (lastIndex != -1)
			{
				lastIndex += searchStr.length();
				
				// Found one "it", assert that the regex can find it too
				assertEquals(true, r.test(lipsum));
			}
		}
		
		// There shouldn't be one now
		assertEquals(false, r.test(lipsum));
		
		r = new RegExp("\\((\\d{3,3})\\)(?: |-)?\\d{3,3}(?: |-)?\\d{4,4}", "g");
		assertEquals(true, r.test("blah(617) 555-1234 blah"));
		assertEquals(false, r.test("blah (617)5555-1234 blah blah"));
	}
	
	@Test
	public void testExec()
	{
		r = new RegExp("\\((\\d{3,3})\\)(?: |-)?\\d{3,3}(?: |-)?\\d{4,4}", "g");
		m = r.exec("blah(617) 555-1234");
		
		assertEquals("(617) 555-1234", m.get(0));
		assertEquals("617", m.get(1));
	}
	
	@Test
	public void testToString()
	{
		r = new RegExp("");
		assertEquals("//", r.toString());
		
		r = new RegExp("test");
		assertEquals("/test/", r.toString());
		
		r = new RegExp("test", "gi");
		assertEquals("/test/" + r.flags(), r.toString());
	}
	
	@Test
	public void testSource()
	{
		r = new RegExp("");
		assertEquals("", r.source());
		
		r = new RegExp("test", "gi");
		assertEquals("test", r.source());
	}
}
