/* 1
A ::= B | C
B ::= bE
C ::= cF | d C
*/

void A(){
	if(token == 'b'){
		nextToken();
		B();
	}
	else
		C();
}

void B(){
	E();
}

void C(){
	if(token == 'c'){
		nextToken();
		F();
	}
	else
		if(token == 'd'){
			nextToken();
			C();
		}
		else
			error();
			
}


/* 2
A ::= bB | cC | dD
*/

void A(){
	if(token == 'b'){
		nextToken();
		B();
	}else if(token == 'c'){
		nextToken();
		C();	
	}else if(token == 'd'){
		nextToken();
		D();	
	}else error();

}

/* 3
A ::= [bB]C
*/

void A(){
	
	if(token=='b'){
		nextToken();
		B();
	} 
	
	C();
}

/* 4
A ::= [B]C
B ::= bD | cE
*/

void A(){
	if(token == 'b' || token == 'c'){		
		B();
	}

	C();
}

void B(){
	if(token == 'b'){
		nextToken();
		D();
	} else
		if(token == 'c'){
			nextToken();
			E();
		} else
			error();
}

/* 5
A ::= B[C]D
C ::= cE
*/

void A(){
	B();
	if( token=='c'){
		nextToken();
		C();
	}
	D();
}

void C(){
	E();
}

/* 6
A ::= [B][C]D
B ::= bE
C ::= cF
*/

void A(){
	if(token == 'b'){
		nextToken();
		B();	
	}
	if(token == 'c'){
		nextToken();
		C();
	}

	D();	
}

void B(){
	
	E();
}

void C(){

	F();
}

/* 7
A ::= B {B}
B ::= bC | cD
*/

void A(){
	B();
	while (token == 'b' || token == 'c')
		B();
}

void B(){
	if (token == 'b'){
		nextToken();
		C();
	}else if (token == 'c'){
		nextToken();
		D();
	}else
		error();
}
/* 8
A ::= {B}
B ::= bC | cD
*/


void A(){


}

void B(){
}

/* 9
A ::= { a [B] }
B ::= bC | cD
*/


void A(){
	while(token == 'a' )
	{
		nextToken();
		if(token == 'b' || token == 'c')
		{
			B();
		}
	}

}

void B(){
	if( token == 'b')
	{
		nextToken();
		C();		
	}
	else if( token == 'c'){
		nextToken();
		D();
	}
	else
		error();
	
}

/* 10
A ::= {B | C}
B ::= bfgE | eF
C ::= cG
*/


void A(){
	while(token == 'b' || token == 'e' || token == 'c'){
		if(token == 'c'){
			nextToken();
			C();
		}
		else{
			B();
		}
	}
}

//B ::= bfgE | eF
void B(){
	if(token == 'b'){
		nextToken();
		if(token == 'f')
		{
			nextToken();
			if(token == 'g'){
				nextToken();
				E();
			}
			else
				error();
		}
		else
			error();		
		
	}
	else if (token == 'e'){
		nextToken();
		F();
	}
	else
		error();
}

//C ::= cG
void C(){
	G();	
}

/* LE
E ::= TE'
E' ::= + T E' | 
T ::= F T'
T' ::= * F T' | 
F ::= Numero | (E)
*/
