package com.aplicacao.cursomc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aplicacao.cursomc.domain.Categoria;
import com.aplicacao.cursomc.domain.Cidade;
import com.aplicacao.cursomc.domain.Cliente;
import com.aplicacao.cursomc.domain.Endereco;
import com.aplicacao.cursomc.domain.Estado;
import com.aplicacao.cursomc.domain.ItemPedido;
import com.aplicacao.cursomc.domain.Pagamento;
import com.aplicacao.cursomc.domain.PagamentoComBoleto;
import com.aplicacao.cursomc.domain.PagamentoComCartao;
import com.aplicacao.cursomc.domain.Pedido;
import com.aplicacao.cursomc.domain.Produto;
import com.aplicacao.cursomc.domain.enums.EstadoPagamento;
import com.aplicacao.cursomc.domain.enums.TipoCliente;
import com.aplicacao.cursomc.repositories.CategoriaRepository;
import com.aplicacao.cursomc.repositories.CidadeRepository;
import com.aplicacao.cursomc.repositories.ClienteRepository;
import com.aplicacao.cursomc.repositories.EnderecoRepository;
import com.aplicacao.cursomc.repositories.EstadoRepository;
import com.aplicacao.cursomc.repositories.ItemPedidosRepository;
import com.aplicacao.cursomc.repositories.PagamentoRepository;
import com.aplicacao.cursomc.repositories.PedidoRepository;
import com.aplicacao.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadesRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidosRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto prod1 = new Produto(null,"Computador",2000.00);
		Produto prod2 = new Produto(null,"Impressora",800.00);
		Produto prod3 = new Produto(null,"Mouse",80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1,prod2,prod3));// Arrays. asList passa varios elementos 
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(prod1,prod2,prod3));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlandia",est1);
		Cidade c2 = new Cidade(null,"São Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2,c3));
	
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadesRepository.saveAll(Arrays.asList(c1,c2,c3));
		Cliente cli1 = new Cliente(null,"Maria Slva","maria@gmail.com","36378912377",TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("27363323","938383030"));
		
		Endereco e1 = new Endereco(null,"rua Flores","300","apto 303","Jardim","38220834",c1,cli1);
		Endereco e2 = new Endereco(null,"Avenida Matos","105","Sala 800","Centro","38777012",c2,cli1);
		
		cli1.getEndereco().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null,sdf.parse("30/09/2017 10:32"),cli1,e1);
		Pedido ped2 = new Pedido(null,sdf.parse("10/10/2017 19:35"),cli1,e2);
		
		Pagamento pag1 = new PagamentoComCartao(null,EstadoPagamento.QUITADO,ped1,6);
		ped1.setPagamento(pag1);
		
		Pagamento pag2 = new PagamentoComBoleto(null,EstadoPagamento.PENDENTE,ped2,sdf.parse("20/10/2017 00:00"),null);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1,pag2));
		
		ItemPedido ip1 = new ItemPedido(ped1,prod1,0.00,1,2000.00);
		ItemPedido ip2 = new ItemPedido(ped1,prod3,0.00,2,80.00);
		ItemPedido ip3 = new ItemPedido(ped2,prod2,100.00,1,800.00);
		
		ped1.getItems().addAll(Arrays.asList(ip1,ip2));
		ped2.getItems().addAll(Arrays.asList(ip3));
		
		prod1.getItems().addAll(Arrays.asList(ip1));
		prod2.getItems().addAll(Arrays.asList(ip3));
		prod3.getItems().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}
}
