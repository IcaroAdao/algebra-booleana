import java.util.*;

public class Aplicacao {
	public static int[] recuperarEntradas(String frase) {
		int qtdEntradas = Character.getNumericValue(frase.charAt(0));
		int[] entradas = new int[qtdEntradas];
		int i = 1;
		int j = 0;

		while(j != qtdEntradas) {
			if (frase.charAt(i) == '0' || frase.charAt(i) == '1') {
				entradas[j] = Character.getNumericValue(frase.charAt(i));
				j++;
			}
			i++;
		}
		return entradas;
	}
	
	public static String[] mapearIniciosExpressoes(String frase) {
		int aninhamentoAtual = 0;
		int aninhamentoMaximo = 0;
		String parCoordenadaAninhamento = "";
		boolean procurar = true;
		ArrayList<String> mapa = new ArrayList<String>();
		
		for (int i = 0; i < frase.length() && procurar; i++) {
			if (frase.charAt(i) == '(') {
				aninhamentoAtual++;
				parCoordenadaAninhamento = Integer.toString(i) + "*" + Integer.toString(aninhamentoAtual);
				mapa.add(parCoordenadaAninhamento);
				if (aninhamentoAtual > aninhamentoMaximo) aninhamentoMaximo = aninhamentoAtual;
			} else if (frase.charAt(i) == ')') {
				if (aninhamentoAtual > 0) {
					aninhamentoAtual--;
				} else procurar = false;
			}
		}
		
		if (aninhamentoAtual != 0 || !procurar) aninhamentoMaximo = -1;

		mapa.add(Integer.toString(aninhamentoMaximo));
		String[] resultado = new String[mapa.size()];
		for (int i = 0; i < mapa.size(); i++) resultado[i] = mapa.get(i);

		return resultado;
	}
	
	public static String inverterString(String frase) {
		String resultado = "";
		int tamanhoFrase = frase.length();
		
		for (int i = 0; i < tamanhoFrase; i++) {
			resultado += frase.charAt(tamanhoFrase - 1 - i);
		}
		return resultado;
	}
	
	public static String recuperarExpressao(String inicioExpressao, String frase) {
		int posCharSeparador = 0;
		int i = 0;

		while (posCharSeparador == 0) {
			if (inicioExpressao.charAt(i) == '*') posCharSeparador = i;
			i++;
		}

		String grauAninhamento = "";
		String posInicioExpresaoStr = "";

		for (int j = 0; j < posCharSeparador; j++) {
			posInicioExpresaoStr += inicioExpressao.charAt(j);
		}
		for (int l = posCharSeparador + 1; l < inicioExpressao.length(); l++) {
			grauAninhamento += inicioExpressao.charAt(l);
		}

		String operador = "";
		String conteudoParenteses = "(";
		String expressaoCompleta = "";
		int posInicioExpresao = Integer.parseInt(posInicioExpresaoStr);
		int k = posInicioExpresao - 1;
		int qtdParentesesAbertura = 1;
		int qtdParentesesFechamento = 0;

		while (frase.charAt(k) != ' ' && frase.charAt(k) != '(') {
			operador += frase.charAt(k);
			k--;
		}

		operador = inverterString(operador);
		k = posInicioExpresao + 1;

		while(qtdParentesesAbertura != qtdParentesesFechamento && k < frase.length()) {
			conteudoParenteses += frase.charAt(k);
			if (frase.charAt(k) == '(') qtdParentesesAbertura++;
			if (frase.charAt(k) == ')') qtdParentesesFechamento++;
			k++;
		}
		expressaoCompleta = grauAninhamento + operador + conteudoParenteses;
		return expressaoCompleta;
	}
	
	public static char retornarEntradaNumerica (char letraEntrada, int[] entradas) {
		char entrada = '0';

		if (letraEntrada == 'A') entrada = Character.forDigit(entradas[0],10);
		if (letraEntrada == 'B') entrada = Character.forDigit(entradas[1],10);
		if (letraEntrada == 'C') entrada = Character.forDigit(entradas[2],10);
		return entrada;
	}
	
	public static String[] resolverExpressao(String expressao, int[] entradas) {
		String[] resposta = new String[2];
		char operador = expressao.charAt(0);
		String operacao = "";
		int i = 0;
		
		while (expressao.charAt(i) != '(') i++;

		for (int j = i; j < expressao.length(); j++) {
			if ((expressao.charAt(j) >= 'A' && expressao.charAt(j) <= 'C')) {
				char entradaNumerica = retornarEntradaNumerica(expressao.charAt(j), entradas);
				operacao += entradaNumerica;
				operacao += operador;
			} else if (expressao.charAt(j) == '0' || expressao.charAt(j) == '1') {
				operacao += expressao.charAt(j);
				operacao += operador;
			}
		}
		
		int tamanhoOperacao = operacao.length() - 1;
		StringBuffer operacaoBffr = new StringBuffer(operacao);
		
		for (int k = 0; k < tamanhoOperacao; k += 2) {
            if(k + 1 < tamanhoOperacao && k + 2 < tamanhoOperacao) {
                if (operacaoBffr.charAt(k + 1) == 'a') {
                	if (operacaoBffr.charAt(k + 2) == '0' || operacaoBffr.charAt(k) == '0') {
                    	operacaoBffr.setCharAt(k + 2, '0');
                    } else operacaoBffr.setCharAt(k + 2, '1');
                } else if ((k + 1) < tamanhoOperacao && operacaoBffr.charAt(k + 1) == 'o') {
                	if (operacaoBffr.charAt(k + 2) == '1' || operacaoBffr.charAt(k) == '1') {
                    	operacaoBffr.setCharAt(k + 2, '1');
                    } else operacaoBffr.setCharAt(k + 2, '0');
                }
            } else if (operacaoBffr.charAt(k + 1) == 'n') {
            	if (operacaoBffr.charAt(k) == '0') {
            		operacaoBffr.setCharAt(k + 1, '1');
            	} else operacaoBffr.setCharAt(k + 1, '0');
            }
        }
		
		resposta[0] = operador == 'n' ? operacaoBffr.charAt(tamanhoOperacao) + "" : operacaoBffr.charAt(tamanhoOperacao - 1) + "";
		resposta[1] = expressao;
		
		return resposta;
	}
	
	public static String removerGrauAninhamento(String expressao) {
		String resultado = "";
		
		for (int i = 1; i < expressao.length(); i++) resultado += expressao.charAt(i);
		return resultado;
	}

	public static int retornarResultadoExpressao(String frase) {
		int[] entradas = recuperarEntradas(frase);
		String[] iniciosExpressoes = mapearIniciosExpressoes(frase);
		int ultimaPosInicios = iniciosExpressoes.length - 1;
		int grauAninhamentoMaximo = Integer.parseInt(iniciosExpressoes[ultimaPosInicios]);
		ArrayList<String> expressoes = new ArrayList<String>();
	
		for (int i = 0; i < ultimaPosInicios; i++) {
			String inicioExpressao = new String(iniciosExpressoes[i]);
			String expressao = recuperarExpressao(inicioExpressao, frase);
			expressoes.add(expressao);
		}

		for (int grauAtual = grauAninhamentoMaximo; grauAtual > 1; grauAtual--) {
			for (int k = 0; k < expressoes.size(); k++) {
				String expressao = expressoes.get(k);				

				if (Character.getNumericValue(expressao.charAt(0)) == grauAtual) {
					String expressaoSemGrauAninhamento = removerGrauAninhamento(expressao);
					if (expressaoSemGrauAninhamento.length() != 1) {
						String[] resultadoExpressao = resolverExpressao(expressaoSemGrauAninhamento, entradas);

						for (int l = 0; l < expressoes.size(); l++) {
							String expressaoAtual = expressoes.get(l);
							String expressaoResolvida = expressaoAtual.replace(resultadoExpressao[1], resultadoExpressao[0]);
							expressoes.set(l, expressaoResolvida);
						}
					}
				}
			}
		}
		
		String expressaoSemGrauAninhamento = removerGrauAninhamento(expressoes.get(0));
		String[] resultadoFinal = resolverExpressao(expressaoSemGrauAninhamento, entradas);
		return Integer.parseInt(resultadoFinal[0]);
	}

	public static void main(String[] args) {
		String entrada;
		int saida;
		entrada = MyIO.readLine();

		while(!(entrada.equals("0"))) {
			saida = retornarResultadoExpressao(entrada);
			MyIO.println(saida);
			entrada = MyIO.readLine();
		}
	}
}
