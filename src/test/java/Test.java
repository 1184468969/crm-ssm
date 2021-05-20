


public class Test {

    @org.junit.Test
    public void test01(){
        final int size = 10;
        int sum = 0;
        for (int i = 0; i < size; i++){
            if (i%2==0){
                continue;
            }
            for (int j = 0; j < size-5; j++){
                if (j%3==0){
                    ++sum;
                }else {
                    break;
                }
            }
            ++sum;
        }
        System.out.println(sum);
    }

    @org.junit.Test
    public void test02(){
        final int size = 3;
        int sum = 0;
        for (int i=1; i<size; i++){
            for (int j=i; j<size; j++){
                ++sum;
            }
            ++sum;
        }
        System.out.println(sum);
    }
}
