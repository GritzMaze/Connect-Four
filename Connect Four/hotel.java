import java.util.Arrays;
import java.util.Scanner;

public class hotel {

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		int N = input.nextInt();
		int K = input.nextInt();
		input.close();
		
		boolean arr[] = new boolean[N];
		Arrays.fill(arr, Boolean.TRUE);
		for(int i = 2; i <= K; i++)
		{
			for(int f = i; f <= N; f+=i)
			{
				if(arr[f-1] == true)
				{
					arr[f-1] = false;
				}
				else
				{
					arr[f-1] = true;
				}
			}
		}
		for(int i = 0; i < N; i++)
		{
			if(arr[i] == true) System.out.print(i + 1 + " ");
		}
	}
}
