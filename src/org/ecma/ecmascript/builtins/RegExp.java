package org.ecma.ecmascript.builtins;

import java.util.regex.*;

/**
 * An ECMAScript-esque wrapper for the Java {@code Pattern} class. Some
 * Java-specific regular expression features are unsupported due to this
 * wrapper.
 * 
 * TODO: Modify unit tests from ECMA TC-39 to unit test this package
 * 	https://github.com/tc39/test262/tree/master/test/built-ins/RegExp
 * 
 * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/RegExp
 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/RegExp.html
 * @author antonsynd
 */
public class RegExp
{
	
	//----------------------------------
	//  Private properties
	//----------------------------------
	
	private boolean _global;
	private int _lastIndex;
	private Pattern _pattern;
	
	//----------------------------------
	//  Constructors
	//----------------------------------
	
	/**
	 * 
	 * 
	 * @param pattern The pattern can be anything that is valid in Java's 
	 * implementation in the {@code Pattern} class.
	 * @param flags The flags that are supported are {@code g} (global), 
	 * {@code i} (ignore case), {@code s} (dot all), and {@code m} 
	 * (multiline).
	 */
	public RegExp(String pattern, String flags)
	{
		if (pattern == null)
		{
			// TODO: throw argument error
		}
		
		int bitMaskedFlags = 0;
		
		for (int i = 0, len = flags.length(); i < len; i++)
		{
			switch (flags.charAt(i))
			{
				case 'g':
				{
					_global = true;
				}
					break;
				case 'i':
				{
					bitMaskedFlags |= Pattern.CASE_INSENSITIVE;
				}
					break;
				case 'm':
				{
					bitMaskedFlags |= Pattern.MULTILINE;
				}
					break;
				case 's':
				{
					bitMaskedFlags |= Pattern.DOTALL;
				}
					break;
				default:
				{
					// Pass
				}
					break;
			}
		}
		
		_pattern = Pattern.compile(pattern, bitMaskedFlags);
	}

	/**
	 * 
	 * @param pattern The pattern can be anything that is valid in Java's 
	 * implementation in the {@code Pattern} class.
	 */
	public RegExp(String pattern)
	{
		this(pattern, "");
	}
	
	//----------------------------------
	//  Overridden methods
	//----------------------------------
	
	@Override
	public String toString()
	{
		return "/" + _pattern.toString() + "/" + flags();
	}
	
	//----------------------------------
	//  Regular methods
	//----------------------------------
	
	/**
	 * 
	 * 
	 * @param text
	 * @return 
	 */
	public MatchResult exec(String text)
	{
		Matcher matcher = _pattern.matcher(text);
		if (global())
		{
			matcher.region(lastIndex(), text.length());
		}
		
		MatchResult result = null;
		
		if (matcher.find())
		{
			result = new MatchResult(matcher.start(), text);
			if (global())
			{
				setLastIndex(matcher.end());
			}
			
			int len = matcher.groupCount() + 1;
			
			for (int i = 0; i < len; i++)
			{
				result.add(matcher.group(i));
			}
		}
		
		return result;
	}

	// TODO: Double-check specification
	public boolean test(String text)
	{
		Matcher matcher = _pattern.matcher(text);
		boolean result = matcher.find(global() ? lastIndex() : 0);
		
		if (result && global())
		{
			setLastIndex(matcher.end());
		}
		
		return result;
	}
	
	//----------------------------------
	//  Getter/setter methods
	//----------------------------------
	
	public boolean dotall()
	{
		return (_pattern.flags() & Pattern.DOTALL) != 0;
	}
	
	public String flags()
	{
		String flagString = "";
		
		if (global())
		{
			flagString += "g";
		}
		
		if ((_pattern.flags() & Pattern.CASE_INSENSITIVE) != 0)
		{
			flagString += "i";
		}
		
		if ((_pattern.flags() & Pattern.DOTALL) != 0)
		{
			flagString += "s";
		}
		
		if ((_pattern.flags() & Pattern.MULTILINE) != 0)
		{
			flagString += "m";
		}
		
		return flagString;
	}
	
	public boolean global()
	{
		return _global;
	}

	public boolean ignoreCase()
	{
		return (_pattern.flags() & Pattern.CASE_INSENSITIVE) != 0;
	}
	
	public boolean multiline()
	{
		return (_pattern.flags() & Pattern.MULTILINE) != 0;
	}

	public int lastIndex()
	{
		return _lastIndex;
	}
	
	public void setLastIndex(int value)
	{
		if (value < 0)
		{
			// TODO: throw argument error
		}
		
		// Absence of "g" flag forces 0
		_lastIndex = global() ? value : 0;
	}
	
	public int length()
	{
		return 2;
	}
	
	public String source()
	{
		return _pattern.toString();
	}
}
