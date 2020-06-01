package LL1;
import java.util.*;

import lexer.Code;
import lexer.Token;

/*  
 * 
 *             
 *             
�Բ���ʽ���б�� 1-12    select��
1��E��TE��              {(,i}
2��E����ATE��            {+,-}
3��E���� ��         	  {$,)}
4��T��FT��              {(,i}
5��T����MFT��            {*,/}
6��T���� ��              {+,-,$,)}
7��F��(E)              {(  }
8��F�� i               { i }
9��A �� +               {+}
10��A �� -             { - }
11��M �� *             { * }
12��M-> /             { / }

Ԥ�������
	      0      1      2     3       4       5       6        7        
	      i      (      )     +       -       *       /        $          
0��E     E��TE��  E��TE��          
1��E1                  E���� �� E����ATE��  E����ATE��                  E���� ��
2��T      T��FT�� T��FT��                                                 
3��T1                  T���� ��  T���� ��  T���� ��  T����MFT��  T����MFT��   T���� ��
4��F      F�� i   F��(E)                 
5��A                        A �� +     A �� -
6��M                                          M �� *   M �� /

      first        follow
0��E    (,i          ),$
1��E1  +,-,��         $,)     
2��T   (, i        +,-,$,)
3��T1   ��,*,/      +,-,$,)
4��F    (,i       *,/,+,-,$,)
5��A    +,-        (,i
6��M    *,/        (,i

�Է��ս���ź��ս���ű���   �����ս������Ԥ��������д洢���±��д�СΪ10��ƫ����     [code-10]
0  E      10  i
1  E1     11  (
2  T      12  )
3  T1     13  +
4  F      14  -
5  A      15  *
6  M      16  /
          17  $
		  18  ��(~)



���пմ�����~���

*/

public class PredictTable {
	
	int NTnum = 7;       //���ս���Ÿ��� Ԥ������������
	int Tnum = 8;        //������Ԫ����
	int gennum = 12;       //����ʽ����
	int flag=0;
    int[][] predtable=new int[NTnum][Tnum];    //Ԥ���������Ų���ʽ�ı��
    
    Stack<Integer> s = new Stack<Integer>();
    
    int[][] generator=new int[][] {        //��Ų���ʽ  ��һ�����ִ������ʽ��
    	{0,2,1},
    	{1,5,2,1},
    	{1,18},
    	{2,4,3},
    	{3,6,4,3},
    	{3,18},
    	{4,11,0,12},
    	{4,10},
    	{5,13},
    	{5,14},
    	{6,15},
    	{6,16}    	
    };
    
    
    void PushGen(int n) {   //�����Ϊn�Ĳ���ʽ���Ҳ�ѹ��ջ��
    	for(int index = generator[n-1].length-1;index>=1;index--) {
    		if(generator[n-1][index]!=18)               //������ǿմ�
    			s.push(generator[n-1][index]);
    	}
    }
    
    
   
    boolean IsT(int code) {            //�ж��Ƿ�Ϊ�ս����
    	if (code>=10&&code<=16||code==18) return true;
    	return false;
    }
    
    boolean IsNT(int code) {            //�ж��Ƿ�Ϊ���ս����
    	if (code>=0&&code<=6) return true;
    	return false;
    }
    
    boolean IsDollar(int code) {            //�ж��Ƿ�Ϊ��Ԫ����
    	if (code==17) return true;
    	return false;
    }
    
    void error() {                                  //��ƥ��  ����������Ϣ
    	flag=1;   	
    	System.out.println("ƥ��ʧ��");
    	System.exit(0);
    	
    }
    void error(String s) {                                  //��ƥ��  ����������Ϣ
    	flag=1;   	
    	System.out.println(s);
    	
    }
    
	public void printgenerator(int num) {           //����ǰ���õĲ���ʽ��ӡ����

		switch(num-1) {
			case 0:
				System.out.println("E->TE'");break;
			case 1:
				System.out.println("E'->ATE'");break;
			case 2:
				System.out.println("E'->��");break;
			case 3:
				System.out.println("T->FT'");break;
			case 4:
				System.out.println("T'->MFT'");break;
			case 5:
				System.out.println("T'->��");break;
			case 6:
				System.out.println("F->(E)");break;
			case 7:
				System.out.println("F->i");break;
			case 8:
				System.out.println("A->+");break;
			case 9:
				System.out.println("A->-");break;
			case 10:
				System.out.println("M->*");break;
			case 11:
				System.out.println("M->/");break;

		}
		
	}   
	
	public void PrintInput(int index, ArrayList<Token> tokenlist) {
		
		for(int i = index;i<=tokenlist.size()-1;i++) {
			System.out.print(tokenlist.get(i).val);
		}
		System.out.print("     ");
	}
	
	public void PrintStack(Stack<Integer> s) {
		
		for(int i=s.size()-1;i>=0;i--) {
			System.out.print(toString(s.get(i)));
		}
		System.out.print("     ");
	}
	
    int GetCode(String s) {
    	switch(s) {
    		case "E" : return 0;
    		case "E1": return 1;
    		case "T" : return 2;
    		case "T1": return 3;
    		case "F" : return 4;
    		case "A" : return 5;
    		case "M" : return 6;
    		
    		case "i" : return 10;
    		case "(":  return 11;
    		case ")" : return 12;
    		case "+":  return 13;
    		case "-" : return 14;
    		case "*" : return 15;
    		case "/" : return 16;
    		
    		case "$" : return 17; 
    		case "~" : return 18;
    	}		
    	return -1;	
    }
	
    String toString(int code) {
    	switch(code){
	    	case 0 : return "E";
			case 1 :  return "E1";
			case 2 : return "T";
			case 3 :  return  "T1";
			case 4 : return "F";
			case 5 : return "A";
			case 6 : return "M";
		
			case 10 : return "i";
			case 11:  return "(";
			case 12 : return ")";
			case 13:  return "+";
			case 14 : return "-";
			case 15 : return "*";
			case 16 : return "/";
			
			case 17 : return "$"; 
			case 18 : return "~";
    	}
    	
    	return null;
    }
    public void analyze(ArrayList<Token> tokenlist) {         //LL(1)�﷨����
    	
    	s.push(GetCode("$"));  
    	s.push(0);           //ջ��ʼ��   ��ʼ���ź���Ԫ������ջ
    	
    	int top = s.peek();      //ʼ��ָ��ջ������
    	
    	int index = 11;             
    	Token p = null;//ָ�������еĵ�ǰ����
    	Token dollar=new Token(Code.DOLLAR,"$");
        tokenlist.add(dollar);
        
        PrintStack(s);
		PrintInput(index, tokenlist);
		System.out.print("\n");
		
    	while(top!=GetCode("$")) {
    		
    		p = tokenlist.get(index); 
    		int pcode;
    		pcode=(p.code==Code.ID? GetCode("i"): GetCode(p.val));
    		
    		if(pcode==-1) error("������ŷǷ�������");
    		
    		if(top==pcode) {            //���ջ�����ս���ź͵�ǰ����ƥ��
    			
    			s.pop();
    			index++;
    			PrintStack(s);
    			PrintInput(index, tokenlist);
    			System.out.print("\n");
    			
    		}
    		
    		else if(IsT(top)) {       //����������ս����
    			
    			error();
    		}
    		
    		else if(IsNT(top) && predtable[top][pcode-10]==0) {   //���Ԥ��������и���ĿΪerror
    			
    			error();
    			
    		}
    		
    		else if(IsNT(top) && predtable[top][pcode-10]!=0) {
    			
    			
    			s.pop();
    			PushGen(predtable[top][pcode-10]);
    			
    			PrintStack(s);
    			PrintInput(index, tokenlist);
    			printgenerator(predtable[top][pcode-10]);       //��ӡ��ǰ���õı��ʽ
    			
    		
    		}
    		
    		else error();
    		
    		top=s.peek();
    		   		
    	}
    	
    	if(flag==0) System.out.print("�ñ��ʽ�Ϸ�!!!");
    	else error("����cp���ԺϷ����ʽ!!!");
      	
    }
	
	 public PredictTable() {              //Ԥ��������ʼ��  ��Ϊ0��error
		 predtable[0][0]=1;
		 predtable[0][1]=1;
		 predtable[1][2]=3;
		 predtable[1][3]=2;
		 predtable[1][4]=2;
		 predtable[1][7]=3;
		 predtable[2][0]=4;
		 predtable[2][1]=4;
		 predtable[3][2]=6;
		 predtable[3][3]=6;
		 predtable[3][4]=6;
		 predtable[3][5]=5;
		 predtable[3][6]=5;
		 predtable[3][7]=6;
		 predtable[4][0]=8;
		 predtable[4][1]=7;
		 predtable[5][3]=9;
		 predtable[5][4]=10;
		 predtable[6][5]=11;
		 predtable[6][6]=12;
	}
	
	
}
