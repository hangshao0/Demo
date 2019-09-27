This demo shows how to filter out class org.openj9.test.ivj.Disk from the shared cache. 

1. run

        java -Xshareclasses:name=Cache1,nobootclasspath -cp ./utils.jar org.openj9.test.ivj.Hanoi 2
        Beginning puzzle.  Solving for 2 disks.
        Moved disk 0 to 1
        Moved disk 0 to 2
        Moved disk 1 to 2
        Puzzle solved!

2. Check class org.openj9.test.ivj.Disk is in the shared cache

        java -Xshareclasses:name=Cache1,printStats=romclass
        1: 0x00007F6EC58AACF4 ROMCLASS: org/openj9/test/ivj/Hanoi at 0x00007F6EC4B22000.
                Index 0 in classpath 0x00007F6EC58AAD28
        1: 0x00007F6EC58AACB4 ROMCLASS: org/openj9/test/ivj/Post at 0x00007F6EC4B22A58.       
                Index 0 in classpath 0x00007F6EC58AAD28
        1: 0x00007F6EC58AAC74 ROMCLASS: org/openj9/test/ivj/Disk at 0x00007F6EC4B22DF8.
                Index 0 in classpath 0x00007F6EC58AAD28

3. Destory the shared cache

        java -Xshareclasses:name=Cache1,destroy
        JVMSHRC806I Compressed references persistent shared cache "Cache1" has been destroyed. Use option -Xnocompressedrefs if you want to destroy a non-compressed references cache.

4. run with a filter

        java -Xshareclasses:name=Cache1,nobootclasspath -Xbootclasspath/a:./filter -Dcom.ibm.oti.shared.SharedClassGlobalFilterClass=org.openj9.test.StoreFilter -cp ./utils.jar org.openj9.test.ivj.Hanoi 2
        Beginning puzzle.  Solving for 2 disks.
        Moved disk 0 to 1
        Moved disk 0 to 2
        Moved disk 1 to 2
        Puzzle solved!

5. Check class org.openj9.test.ivj.Disk is not in the shared cache

        java -Xshareclasses:name=Cache1,printStats=romclass
        1: 0x00007FEE99B95CF4 ROMCLASS: org/openj9/test/ivj/Hanoi at 0x00007FEE98E0D000.
                Index 0 in classpath 0x00007FEE99B95D28
        1: 0x00007FEE99B95CB4 ROMCLASS: org/openj9/test/ivj/Post at 0x00007FEE98E0DA58.  
                Index 0 in classpath 0x00007FEE99B95D28
        
NOTES:

A. I use option "nobootclasspath" becasue I do not want to see hundreds of bootstrap classes in the output of 
   "-Xshareclasses:name=Cache1,printStats=romclass"

B. Class org.openj9.test.ivj.Disk will be loaded by application class loader which needs to know the classes to be 
   filtered out at its initialization. So the filter class, org.openj9.test.StoreFilter in the example, needs 
   to be loaded by a different classloader. I use option "-Xbootclasspath/a:./filter", so that it is loaded by
   the bootstrap classloader. 

