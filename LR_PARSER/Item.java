package LR_PARSER;
/**
 * @param generator_num(����ʽ���)     dot_position(���λ��)
 * 
 * 
 * @author������
 */
public class Item {
	int generator_num;  //����ʽ���
	int dot_position;   //���λ��  ��0��ʼ     *d*d*d*  (0,1,2...����ʽ����)
	
	public Item(int generator_num, int dot_position){
		this.generator_num = generator_num;
		this.dot_position = dot_position;
	}
	
	public boolean equals(Item item) {
		return (this.generator_num == item.generator_num&&this.dot_position == item.dot_position);
	}
}
