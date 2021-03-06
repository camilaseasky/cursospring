package com.cursospring.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursospring.cursomc.domain.Categoria;
import com.cursospring.cursomc.domain.Cidade;
import com.cursospring.cursomc.domain.Cliente;
import com.cursospring.cursomc.domain.Endereco;
import com.cursospring.cursomc.domain.Estado;
import com.cursospring.cursomc.domain.ItemPedido;
import com.cursospring.cursomc.domain.Pagamento;
import com.cursospring.cursomc.domain.PagamentoComBoleto;
import com.cursospring.cursomc.domain.PagamentoComCartao;
import com.cursospring.cursomc.domain.Pedido;
import com.cursospring.cursomc.domain.Produto;
import com.cursospring.cursomc.domain.enums.EstadoPagamento;
import com.cursospring.cursomc.domain.enums.TipoCliente;
import com.cursospring.cursomc.repositories.CategoriaRepository;
import com.cursospring.cursomc.repositories.CidadeRepository;
import com.cursospring.cursomc.repositories.ClienteRepository;
import com.cursospring.cursomc.repositories.EnderecoRepository;
import com.cursospring.cursomc.repositories.EstadoRepository;
import com.cursospring.cursomc.repositories.ItemPedidoRepository;
import com.cursospring.cursomc.repositories.PagamentoRepository;
import com.cursospring.cursomc.repositories.PedidoRepository;
import com.cursospring.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
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
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Inform??tica");
		Categoria cat2 = new Categoria(null, "Escrit??rio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
				
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "S??o Paulo");
		
		Cidade c1 = new Cidade(null, "Uberl??ncia", est1);
		Cidade c2 = new Cidade(null, "S??o Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678910", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("66999338898", "65999998888"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "130", "rua b", "Coophema", "78000000", cli1, c1);
		Endereco e2 = new Endereco(null, "Av Matos", "200", "ap 20", "Nova Morada", "78000000", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2021 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2021 08:20"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2021 10:00"), null );
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));	
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido itemPed1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido itemPed2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		
		ItemPedido itemPed3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItems().addAll(Arrays.asList(itemPed1, itemPed2));
		ped2.getItems().addAll(Arrays.asList(itemPed3));
		
		p1.getItems().addAll(Arrays.asList(itemPed1));
		p2.getItems().addAll(Arrays.asList(itemPed3));
		p3.getItems().addAll(Arrays.asList(itemPed2));
		
		itemPedidoRepository.saveAll(Arrays.asList(itemPed1, itemPed2, itemPed3));
		
	}

}
