
package com.m4rc310.financas.parts.pdv;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.financas.models.Item;
import com.m4rc310.financas.models.Produto;
import com.m4rc310.financas.parts.PartDefault;

public class PartPDVListBuys extends PartDefault {
	@Inject
	public PartPDVListBuys() {

	}

	@PostConstruct
	public void postConstruct(Composite content) {

		content.setLayout(new GridLayout());
		pc.clearMargins(content);

		Composite parent = getComposite(content, 3);
		pc.setMargins(5, parent);

		pc.fillHorizontalComponent(parent);

		Label labelBuy = pc.getLabel(parent, getTextLabel(m.labelBuy, 10), SWT.RIGHT);

		pc.getLabel(parent, ":");

		Label labelBuyId = pc.getLabel(parent, String.format(m.labelBuyId, 12L));
		pc.setStyledClassname(labelBuyId, "LabelBuyId");

		Label labelBuyClient = pc.getLabel(parent, getTextLabel(m.labelBuyClient, 10), SWT.RIGHT);

		pc.getLabel(parent, ":");

		Link linkClient = new Link(parent, SWT.NONE);
		linkClient.setText(m.textUndefined);

		Composite parentSearch = getComposite(content, 2);
		pc.fillHorizontalComponent(parentSearch);
		pc.setMargins(3, parentSearch);

		pc.getIcon(parentSearch, GLOBAL$plugin_id, "icons/stock_id.png");

		Text textSearch = pc.getText(parentSearch, m.empty,
				SWT.BORDER | SWT.ICON_CANCEL | SWT.SEARCH | SWT.ICON_SEARCH);
		pc.fillHorizontalComponent(textSearch);
		pc.configureUpperCase(textSearch);
		textSearch.setMessage(m.textInformBarcode);

		Composite parentList = getComposite(content, 1);
		pc.fillHorizontalComponent(parentList);
		pc.clearMargins(parentList);

		TableViewer tableList = pc.getTableViewer(parentList);
		tableList.getTable().setHeaderVisible(false);
		pc.fillHorizontalComponent(tableList.getTable()).heightHint = GridData.FILL_VERTICAL;

		pc.createCollumn(tableList, SWT.CENTER, "#", 0, new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Item item = (Item) cell.getElement();
				String sitem = "#%03d";
				sitem = String.format(sitem, item.getItem());
				cell.setText(sitem);
				super.update(cell);
			}
		});
		
		pc.widtCollumn(tableList, 0, 5);
		
		pc.createCollumn(tableList, SWT.NONE, m.titleProdutoDescription, 0,  new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Produto p = ((Item) cell.getElement()).getProduto();
				String text = String.format("(%010d) %s", 
						p.getCodigo(), p.getDescricao().toUpperCase());
				cell.setText(text);
				super.update(cell);
			}
		});
		
		pc.widtCollumn(tableList, 1, 30);
		
		pc.createCollumn(tableList, SWT.RIGHT, "", 0,  new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Produto p = ((Item) cell.getElement()).getProduto();
				String text = MessageFormat.format("{0,number,\\u00A4#}", p.getValor());
				cell.setText(text);
				super.update(cell);
			}
		});
		
		pc.widtCollumn(tableList, 2, 6);
		
//		pc.createCollumn(tableList, SWT.CENTER, m.titleProdutoAmountAbbreviated, 0);
//		pc.createCollumn(tableList, SWT.CENTER, m.titleProdutoValue, 0);
//		pc.createCollumn(tableList, SWT.CENTER, m.titleProdutoValueTotal, 0);
//		
//		
		

//		pc.widtCollumn(tableList, 2, 7);
//		pc.widtCollumn(tableList, 3, 7);
//		pc.widtCollumn(tableList, 4, 7);

		List<Item> itens = new ArrayList<>();
		itens.add(getItem(1, 23243434L, "Coca-Cola 200ml", BigDecimal.valueOf(23.45), BigDecimal.ONE));
		itens.add(getItem(2, 23L, "Batata", BigDecimal.valueOf(1.890), BigDecimal.valueOf(0.800)));
		itens.add(getItem(3, 320L, "Pepino Caipira", BigDecimal.valueOf(3.90), BigDecimal.valueOf(1.230)));
		itens.add(getItem(4, 7389555L, "Cola Tenás 20ml", BigDecimal.valueOf(5.50), BigDecimal.ONE));

		tableList.setInput(itens);

	}

//	private String getItemString(String descricao, int w) {
//		int res = w - descricao.length();
//		StringBuilder sb = new StringBuilder();
////		sb.append(descricao);
//		
////		if (res > 0) {
//			for (int i = 0; i < 35; i++) {
//				sb.append("M");
//			}
////		}
//		
//		descricao =  sb.toString();
//		
//		System.out.println(descricao.length());
//		
//		return descricao;
//	}

	private Item getItem(int i, Long codigo, String descricao, BigDecimal valor, BigDecimal quantidade) {
		Produto p = new Produto();
		p.setDescricao(descricao);
		p.setCodigo(codigo);
		p.setValor(valor);

		Item item = new Item();
		item.setItem(i);
		item.setProduto(p);
		item.setQuantidade(quantidade);

		return item;

	}

	private String getTextLabel(String text, int nc) {
		text = text.replace(":", "");
//		text = String.format("%"+nc+"s", text);
//		text = text.replace(" ", ".");

		return text;
	}

	@PreDestroy
	public void preDestroy() {

	}

}