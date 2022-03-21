MEMORY
{
  ROM(rx) 	: ORIGIN = 0x00,		LENGTH = 8K
  RAM(rw!x) : ORIGIN = 0x800060,	LENGTH = 512
}

SECTIONS
{
	.text : 
		{
			*(.text);
		} > ROM
 
	.data : 
		{
			*(.data);
		} > RAM AT > ROM  
}