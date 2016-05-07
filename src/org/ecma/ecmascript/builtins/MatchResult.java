package org.ecma.ecmascript.builtins;

import java.util.*;

/**
 * An ECMAScript-esque wrapper for the Java {@code Pattern} class. Some
 * Java-specific regular expression features are unsupported due to this
 * wrapper.
 * 
 * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/RegExp
 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/RegExp.html
 * @author antonsynd
 */
public class MatchResult extends LinkedList<String>
{
	
	//----------------------------------
	//  Private properties
	//----------------------------------
	
	private int _index;
	private String _input;
	
	/**
	 * Auto-generated
	 */
	private static final long serialVersionUID = 6018775203125155422L;
	
	//----------------------------------
	//  Constructors
	//----------------------------------
	
	// Internal
	MatchResult(int index, String input)
	{
		if (index < 0)
		{
			// TODO: throw argument error
		}
		
		if (input == null)
		{
			// TODO: throw argument error
		}
		
		_index = index;
		_input = input;
	}
	
	//----------------------------------
	//  Overridden methods
	//----------------------------------
	
	@Override
	public String toString()
	{
		String result = "";
		
		for (String i : this)
		{
			result += i + ",";
		}
		
		return result;
	}
	
	//----------------------------------
	//  Getter/setter methods
	//----------------------------------
	
	public int index()
	{
		return _index;
	}
	
	public String input()
	{
		return _input;
	}
}
