import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Tests
{
    public static void main(String args[]) throws IOException
    {
        boolean tests = false;
        System.out.println("Starting tests:");
        BTree T = new BTree("2");
        T.createFullTree(System.getProperty("user.dir")+"/bad_passwords.txt");
       tests = tryTest("hellow","111111_2,123456_1,donald_2,google_0,login_2,password_2,querty_1,starwars_2,welcome_2,zxcvbnm_2", T);
       tests = tryTest("123456","111111_1,donald_1,google_0,login_1,password_1,querty_0,starwars_1,welcome_1,zxcvbnm_1", T);
       tests = tryTest("login","111111_1,donald_1,google_0,password_1,querty_0,starwars_1,welcome_1,zxcvbnm_1", T);
       tests = tryTest("password","111111_1,donald_0,google_1,querty_0,starwars_1,welcome_1,zxcvbnm_1", T);
       tests = tryTest("google","111111_1,donald_0,querty_1,starwars_0,welcome_1,zxcvbnm_1", T);
       tests = tryTest("111111","donald_1,querty_1,starwars_0,welcome_1,zxcvbnm_1", T);
       tests = tryTest("querty","donald_1,starwars_0,welcome_1,zxcvbnm_1", T);
       tests = tryTest("zxcvbnm","donald_1,starwars_0,welcome_1", T);
       tests = tryTest("starwars","donald_0,welcome_0", T);
       tests = tryTest("donald","welcome_0", T);
       tests = tryTest("welcome","", T);
        // new tree

        T = new BTree("2");
        T.createFullTree(System.getProperty("user.dir")+"/bad_passwords.txt");
        T.delete("hellow");
        tests = tryTest("google","111111_1,123456_0,donald_1,login_0,password_1,querty_0,starwars_1,welcome_1,zxcvbnm_1", T);
        tests = tryTest("starwars", "111111_1,123456_0,donald_1,login_0,password_1,querty_0,welcome_1,zxcvbnm_1", T);
        tests = tryTest("welcome", "111111_1,123456_0,donald_1,login_0,password_1,querty_0,zxcvbnm_1", T);
        tests = tryTest("zxcvbnm", "111111_1,123456_0,donald_1,login_0,password_1,querty_1", T);
        tests = tryTest("querty", "111111_1,123456_0,donald_1,login_0,password_1", T);
        tests = tryTest("password", "111111_1,123456_0,donald_1,login_1", T);
        tests = tryTest("111111", "123456_1,donald_0,login_1", T);
        tests = tryTest("donald", "123456_0,login_0", T);

        // new tree
        T = new BTree("2");
        T.createFullTree(System.getProperty("user.dir")+"/bad_passwords.txt");
        tests = tryTest("google", "111111_2,123456_1,donald_2,hellow_0,login_2,password_2,querty_1,starwars_2,welcome_2,zxcvbnm_2", T);
        tests = tryTest("123456", "111111_1,donald_1,hellow_0,login_1,password_1,querty_0,starwars_1,welcome_1,zxcvbnm_1", T);

        // new tree
        T = new BTree("2");
        T.createFullTree(System.getProperty("user.dir")+"/bad_passwords.txt");
        T.insert("111112");
        T.insert("111113");
        T.insert("111114");
        tests = tryTest("google", "111111_2,111112_1,111113_2,111114_1,123456_2,donald_0,hellow_2,login_1,password_2,querty_1,starwars_2,welcome_2,zxcvbnm_2", T);
        tests = tryTest("login", "111111_2,111112_1,111113_2,111114_1,123456_2,donald_0,hellow_2,password_2,querty_1,starwars_2,welcome_2,zxcvbnm_2", T);
        tests = tryTest("querty", "111111_2,111112_1,111113_2,111114_0,123456_2,donald_1,hellow_2,password_1,starwars_2,welcome_2,zxcvbnm_2", T);
        if(tests)
        {
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/passedtests.txt"));
            String line = reader.readLine();
            while(line != null)
            {
                System.out.println(line);
                line = reader.readLine();
            }
        }
    }
    public static boolean tryTest(String keyToDelete, String expectedOutput, BTree T) throws IOException
    {
        try
        {
            T.delete(keyToDelete);
            if(T.toString().equals(expectedOutput))
            {
                System.out.println("Passed");
                return true;
            }
            else
            {
                System.out.println("Test failed\n" + "expected output: " + expectedOutput + "\n" + "your output: " + T.toString());
                return false;
            }
        }
        catch(Exception e)
        {
            System.out.println("Test failed while trying to delete: " + keyToDelete);
            e.printStackTrace();
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/skull.txt"));
            String line = reader.readLine();
            while(line != null)
            {
                System.out.println(line);
                line = reader.readLine();
            }
            System.exit(0);
        }
        return false;
    }
}
