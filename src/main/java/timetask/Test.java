package timetask;

public class Test {

	public static void main(String[] args) {
		int cos=7;
		double sum=0;
		int count=22;
		for(int i=1;i<=count*2;i++){
			if(sum<=100){
				sum=sum+cos;
			}else if(sum>100 &&sum<=150){
				sum=sum+cos*0.8;
			}else if(sum>150 && sum<400){
				sum=sum+cos*0.5;
			}
		}
        System.out.println("每月总共花费："+sum);
	}

}
