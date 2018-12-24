package org.tbwork.log.printer;

import org.tbwork.anole.loader.annotion.AnoleConfigLocation;
import org.tbwork.anole.loader.context.Anole;
import org.tbwork.anole.loader.context.AnoleApp;
import org.tbwork.anole.loader.util.AnoleLogger.LogLevel;
import org.tbwork.anole.loader.util.ProjectUtil;

/**
 * Hello world!
 *
 */
@AnoleConfigLocation(locations="*.anole")
public class App 
{
    public static void main( String[] args )
    { 
        AnoleApp.start(LogLevel.DEBUG);
        System.out.println(Anole.getProperty("name"));
    }
}
