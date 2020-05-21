package lexer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/**
 * @author ������
 *
 */
/*
(1)�ؼ��ʣ� begin  end  if   then  else   for  while  do  and  or  not
(2)��ʶ��id�� (_|letter)(_|letter|digit)*  �����ұ�ʶ�������ǹؼ���
(3)�޷�������NUM�����ִ�    (digit)+
(4)������ͷֽ���� +��-��*��/��>��<��=��:=��>=��<=��<>��++��--��(��)��; �� # 
ע�⣺:=��ʾ��ֵ�������#��ʾע�Ϳ�ʼ�Ĳ��֣�;��ʾ��������<>��ʾ���ȹ�ϵ
(5) �հ׷�����" "��"\t"��"\n"�����ڷָ�ID��NUM����������ֽ���͹ؼ��ʣ��ʷ������׶�Ҫ���Կհ׷���

*/
public class Lexer {
	public int line=1;
	private char temp;   //���Ԥ�ȶ������һ���ַ�   
	
	ArrayList<Token> tokenlist=new ArrayList<Token>(11);    //Token���Ͷ�̬����
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	void addtoken(Token t) {
		tokenlist.add(t);
	}

	public Lexer() {               
		addtoken(new Token(Code.BEGIN,"begin"));   //ִ�дʷ�������֮ǰ Ԥ�ȵ���11���ؼ���
		addtoken(new Token(Code.END,"end"));
		addtoken(new Token(Code.IF,"if"));
		addtoken(new Token(Code.THEN,"then"));
		addtoken(new Token(Code.ELSE,"else"));
		addtoken(new Token(Code.FOR,"for"));
		addtoken(new Token(Code.WHILE,"while"));
		addtoken(new Token(Code.DO,"do"));
		addtoken(new Token(Code.AND,"and"));
		addtoken(new Token(Code.OR,"or"));
		addtoken(new Token(Code.NOT,"not"));
	}
	
	 public boolean IsOPorDIV(char c) {
		 if(c=='+'||c=='-'||c=='*'||c=='/'||c=='>'||c=='<'||c==':'||c=='('||c==')'||c==';' ||c=='#'||c==' '||c=='$'||c=='\t'||c=='\n')
				 return true;
		 return false;
	 }
	 
	public boolean IsDigit(char c) {
		return (c>='0'&&c<='9');
	}
	
	public boolean IsLetter_(char c) {
		return (c>='A'&&c<='Z'||c>='a'&&c<='z'||c=='_');
	}
	
	public Token IsKeyword(String s) {       //�жϸõ����Ƿ��ǹؼ���
		Token w;
		for(int i=0;i<11;i++) {
			w=tokenlist.get(i);
			if(s.equals(w.val)) return w;
		}
		return null;
	}
	
	void showerror() {          //�����̨���������Ϣ ��֪����λ��
		
		System.out.println("\nerror occurs at line"+":"+line);
		
	}
	
	void printlist() {
		Code c=new Code();
		Token w;
		for(int i=11;i<tokenlist.size();i++) {
			w=tokenlist.get(i);
			System.out.println("<"+c.toString(w.code)+"  "+w.val+">");
		}
	}
	
	void readchar() throws IOException {
		temp=(char)br.read();
	}
	boolean readchar(char target) {
		try {
			readchar();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return target==temp;	
	}
	
	public void TokenScan() throws IOException {
		
		int flag=0;                 //�ж��Ƿ��Ѿ���ȡ����һ���ַ�
		
		while(temp!='$') {
			
			do {                //���˿հ׷���ע��
				if(flag==0) readchar();
				flag=0;
				if(temp==' '||temp=='\t') continue;
				else if(temp=='\n') {line++;continue;}
				else if(temp=='#') 
				{              //�����һ���ַ���ע�ͷ�
					do {
						readchar();
					   }while(temp!='\n');				
				}
				else break;
			}while(true);
			
				switch(temp) {
					case '+':
					{	
						
						if(readchar('+')) addtoken(new Token(Code.OP,"++")); 
						else {flag=1;addtoken(new Token(Code.OP,"+"));}
						break;
					}
					case '=':
					{
						addtoken(new Token(Code.OP,"="));	
						break;
					}
					
					case '>':
					{	
						
						if(readchar('=')) addtoken(new Token(Code.OP,">=")); 
						else {flag=1;addtoken(new Token(Code.OP,">"));}
						break;
					}
					
					case '<':
					{	
						
						if(readchar('=')) addtoken(new Token(Code.OP,"<=")); 
						else if(temp=='>') addtoken(new Token(Code.OP,"<>"));
						else {flag=1;addtoken(new Token(Code.OP,"<"));}
						break;
					}
					
					case '-':
					{	
						
						if(readchar('-')) addtoken(new Token(Code.OP,"--")); 
						else {flag=1;addtoken(new Token(Code.OP,"-"));}
						break;
					}
					
					case ':':
					{
						
						if(readchar('=')) addtoken(new Token(Code.OP,":=")); 
						else {flag=1;showerror();}           //�������κδʷ���Ԫ
						break;
					}
					
					case '*':
					{
						addtoken(new Token(Code.OP,"*"));
						break;
					}
					
					case '/':
					{
						addtoken(new Token(Code.OP,"/"));
						break;
					}
					
					case '(':
					{
						addtoken(new Token(Code.DIV,"("));
						break;
					}
					
					case ')':
					{
						addtoken(new Token(Code.DIV,")"));
						break;
					}
					
					case ';':
					{
						addtoken(new Token(Code.DIV,";"));
						break;
					}
				}
			
				if(IsDigit(temp)) {                 //�ж��Ƿ�Ϊ���ִ�
						int val=0;
					    do{
					        val=val*10+Character.digit(temp,10);
					        temp=(char)br.read();
					    }while(IsDigit(temp));
					    flag=1;
					    
					    if(IsOPorDIV(temp)==false) showerror();     //����ĸ��ͷ�ı�ʶ���Ƿ�
					    else addtoken(new Token(Code.NUM,""+val));
				}
				
				else if(IsLetter_(temp)){                 //�����һ���ַ�����ĸ�����»���
					StringBuffer sb=new StringBuffer();
					do {
						sb.append(temp);
						readchar();
					}while(IsLetter_(temp)||IsDigit(temp));
					flag=1;
					String SB=sb.toString();
					Token t;
					if((t=IsKeyword(SB))!=null)                           //������������иô�����ӹؼ���
						addtoken(t);
					else {                         //���û���ڱ�������˵������һ����ʶ��,���������tokenlist��
						t=new Token(Code.ID,SB);
						addtoken(t); 
					}
				}
			}
		br.close();
		}
		
	}
	
	

