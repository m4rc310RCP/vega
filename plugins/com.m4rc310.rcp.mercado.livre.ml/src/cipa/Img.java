package cipa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.m4rc310.rcp.mercado.livre.ml.cipa.models.GrupoLocais;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Local;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Risco;

public class Img {

	private static final int RISCO_P = 1;
	private static final int RISCO_M = 2;
	private static final int RISCO_G = 3;

//case 2:
//	return new Color(QUIMICO_VERMELHO);
//case 1:
//	return new Color(FISICO_VERDE);
//case 4:
//	return new Color(ERGONOMICO_AMARELO);
//case 3:
//	return new Color(BIOLOGICO_MARROM);
//case 5:
//	return new Color(MECANICO_AZUL);

	private static final int FISICO_VERDE = 1;
	private static final int QUIMICO_VERMELHO = 2;
	private static final int BIOLOGICO_MARROM = 3;
	private static final int ERGONOMICO_AMARELO = 4;
	private static final int MECANICO_AZUL = 5;

	public static void main(String[] args) throws IOException {

		List<GrupoLocais> grupos = new ArrayList<>();

		GrupoLocais grupo = new GrupoLocais();
		grupo.setId(1L);
		grupo.setDescricao("Casa de Máquinas Principal");
//		grupo.setDescricao("Silo");

		Local local = new Local();

		local.setRiscos(new ArrayList<>());

		Risco r = new Risco();
		r.setDescricao("Risco G");
		r.setTamanho(RISCO_G);
		r.setLocal(local);
		r.setGrupoRisco(ERGONOMICO_AMARELO);
//		r.setGrupoRisco(BIOLOGICO_MARROM);

		local.getRiscos().add(r);

		r = new Risco();
		r.setDescricao("Risco G");
		r.setTamanho(RISCO_G);
		r.setLocal(local);
		r.setGrupoRisco(BIOLOGICO_MARROM);

		local.getRiscos().add(r);

		r = new Risco();
		r.setDescricao("Risco G");
		r.setTamanho(RISCO_G);
		r.setLocal(local);
		r.setGrupoRisco(MECANICO_AZUL);

		local.getRiscos().add(r);

		r = new Risco();
		r.setDescricao("Risco M");
		r.setTamanho(RISCO_M);
		r.setLocal(local);
		r.setGrupoRisco(MECANICO_AZUL);
//		local.getRiscos().add(r);

		r = new Risco();
		r.setDescricao("Risco M");
		r.setTamanho(RISCO_M);
		r.setLocal(local);
		r.setGrupoRisco(ERGONOMICO_AMARELO);

//		local.getRiscos().add(r);

		r = new Risco();
		r.setDescricao("Risco P");
		r.setTamanho(RISCO_P);
		r.setLocal(local);
		r.setGrupoRisco(QUIMICO_VERMELHO);

//		local.getRiscos().add(r);

		r = new Risco();
		r.setDescricao("Risco P");
		r.setTamanho(RISCO_P);
		r.setLocal(local);
		r.setGrupoRisco(FISICO_VERDE);

//		local.getRiscos().add(r);

		grupo.setLocais(Arrays.asList(local));

		System.out.println(grupo.getLocais().size());

		grupos.add(grupo);

		File outputfile = new File("image.png");

//		BufferedImage img = getIcon(grupos, 1L, 50, 5);
//		BufferedImage img = createImgRiscoFinal2(grupo, 20);
		BufferedImage img = getIcon(40, .9f);

		ImageIO.write(img, "png", outputfile);
		System.out.println(outputfile.getAbsolutePath());
	}

//	private static final Map<String, Image> mapImages = new HashMap<>();

	public static Image getSingleIcon(Local local, int h) {
		BufferedImage bi = new BufferedImage(h, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();

		applyQualityRenderingHints(g);

		List<Risco> riscos = local.getRiscos();

		List<Integer> grupos = new ArrayList<>();

		for (Risco r : riscos) {
			int gr = r.getGrupoRisco();
			if (!grupos.contains(gr)) {
				grupos.add(gr);
			}
		}

		if (grupos.isEmpty()) {
			g.setColor(Color.DARK_GRAY);
			g.drawOval(0, 0, h, h);
			int color = bi.getRGB(0, 0);
			Image image = makeColorTransparent(bi, new Color(color), 0.01f);

			return image;
		}

		BigDecimal props = BigDecimal.ONE;
		props = props.divide(BigDecimal.valueOf(grupos.size()), 5, BigDecimal.ROUND_CEILING);

		BigDecimal size = BigDecimal.valueOf(360).multiply(props);

		int arc = 0;

		for (Integer v : grupos) {
			g.setColor(getColor(v));
			g.fillArc(0, 0, h, h, arc, size.intValue());
			arc += size.intValue();
		}

		g.setColor(Color.DARK_GRAY);
		g.drawOval(0, 0, h, h);

		int color = bi.getRGB(0, 0);
		Image image = makeColorTransparent(bi, new Color(color), 0.01f);

		return image;
	}

	public static BufferedImage getIcon_(int risco, int h) {
		
		
		BigDecimal xy = BigDecimal.valueOf(h).multiply(BigDecimal.valueOf(.1));
		BigDecimal bh = BigDecimal.valueOf(h).multiply(BigDecimal.valueOf(.8));
		

		BufferedImage bi = new BufferedImage(h, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();

		applyQualityRenderingHints(g);

		g.setColor(getColor(risco));
		g.fillOval(xy.intValue(), xy.intValue(), bh.intValue(), bh.intValue());
		
		g.setColor(Color.DARK_GRAY);
		g.drawOval(xy.intValue(), xy.intValue(), bh.intValue(),bh.intValue());
		
		return bi;
		
	}

	public static BufferedImage getIcon(int h, float scale) {
		BigDecimal bh = BigDecimal.valueOf(h);
		BigDecimal bs = BigDecimal.valueOf(scale);
		

		BigDecimal xy = bh.multiply(BigDecimal.valueOf(0.03));
		
		bh = bh.multiply(bs);

		BufferedImage bi = new BufferedImage(h, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();
		applyQualityRenderingHints(g);

//		g.setColor(Color.YELLOW);
//		
//		g.fillRect(0, 0, h, h);
		
		
//		bh = bh.multiply(BigDecimal.valueOf(.9));
		
		BigDecimal dois = BigDecimal.valueOf(2);
		
		xy = BigDecimal.valueOf(h - bh.floatValue());
		xy = xy.divide(dois, 5, BigDecimal.ROUND_CEILING);
		
		g.setColor(Color.WHITE);
		g.fillOval(xy.intValue(), xy.intValue(), bh.intValue(), bh.intValue());

		g.setColor(Color.DARK_GRAY);
		g.drawOval(xy.intValue(), xy.intValue(), bh.intValue(), bh.intValue());

		return bi;
	}
	
	
	public static Image getIcon(int risco, int h) {

		BufferedImage bi = new BufferedImage(h, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();

		applyQualityRenderingHints(g);

		g.setColor(getColor(risco));
		g.fillOval(0, 0, h, h);

		g.setColor(Color.DARK_GRAY);
		g.drawOval(0, 0, h, h);

		int color = bi.getRGB(0, 0);
		Image image = makeColorTransparent(bi, new Color(color), 0.01f);

		return image;
	}

	public static Image makeColorTransparent(BufferedImage im, final Color color, float threshold) {
		ImageFilter filter = new RGBImageFilter() {
			public float markerAlpha = color.getRGB() | 0xFF000000;

			public final int filterRGB(int x, int y, int rgb) {
				int currentAlpha = rgb | 0xFF000000; // just to make it clear, stored the value in new variable
				float diff = Math.abs((currentAlpha - markerAlpha) / markerAlpha); // Now get the difference
				if (diff <= threshold) { // Then compare that threshold value
					return 0x00FFFFFF & rgb;
				} else {
					return rgb;
				}
			}
		};
		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}

	public static BufferedImage getIcon(List<GrupoLocais> grupos, Long id, int h, int fontSize) {

		mapRiscos.clear();

		for (GrupoLocais gl : grupos) {

			if (!gl.getId().equals(id)) {
				continue;
			}

			for (Local local : gl.getLocais()) {
				for (Risco r : local.getRiscos()) {
					int tm = r.getTamanho();
					if (!mapRiscos.containsKey(tm)) {
						mapRiscos.put(tm, new ArrayList<>());
					}

					Integer g = r.getGrupoRisco();

					List<Integer> gps = mapRiscos.get(tm);
					if (!gps.contains(g)) {
						gps.add(g);
					}
				}
			}
		}

		GrupoLocais grupo = null;
		for (GrupoLocais g : grupos) {
			if (g.getId().equals(id)) {
				grupo = g;
				break;
			}
		}

		if (grupo == null) {
			return null;
		}

		System.out.println(grupo.getDescricao());

//		BufferedImage img = createImgRiscoFinal(grupo, h, fontSize);
		BufferedImage img = createImgRiscoFinal2(grupo, h);

		return img;

	}

	public static BufferedImage createImgRiscoFinal2(GrupoLocais gl, int h) {
		BigDecimal bh = BigDecimal.valueOf(1.06);
		bh = BigDecimal.valueOf(h).multiply(bh);

		BigDecimal bsp = BigDecimal.valueOf(h * 0.05);
		BigDecimal bw = bsp;
		bw = bw.add(bsp);
		bw = bw.add(BigDecimal.valueOf(h * 0.9));
		bw = bw.add(bsp);
		bw = bw.add(BigDecimal.valueOf(h * 0.7));
		bw = bw.add(bsp);
		bw = bw.add(BigDecimal.valueOf(h * 0.5));
		bw = bw.add(bsp);

		Dimension d = new Dimension(bw.intValue(), bh.intValue());

		BufferedImage bi = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();
		applyQualityRenderingHints(g);

		printIcons(g, gl, d.height, bw, BigDecimal.ZERO, BigDecimal.ZERO);

		return bi;
	}

	public static BufferedImage createImgRiscoFinal(GrupoLocais gl, int h, int fontSize) {

		BigDecimal bh = BigDecimal.valueOf(0.5);
		bh = BigDecimal.valueOf(h).multiply(bh);

		Dimension iconWidth = calculeWidth(gl, h);

		String descricao = String.format("%02d - %s", gl.getId(), gl.getDescricao());
		descricao = descricao.toUpperCase();

		Dimension fontDimension = getFontMetrics(gl, descricao, fontSize);

		BigDecimal bw = BigDecimal.valueOf(fontDimension.getWidth());

		double width = Math.max(iconWidth.getWidth(), bw.doubleValue());
		bw = BigDecimal.valueOf(width);

		BigDecimal sp = BigDecimal.valueOf(0.4);
		sp = BigDecimal.valueOf(fontDimension.getHeight()).multiply(sp);

		BufferedImage bi = new BufferedImage(bw.intValue(),
				iconWidth.height + fontDimension.height + (sp.intValue() * 2), BufferedImage.TYPE_INT_ARGB);
//		BufferedImage bi = new BufferedImage(bw.intValue(),
//				iconWidth.height , BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();

		applyQualityRenderingHints(g2d);

		g2d.setColor(Color.BLACK);

		g2d.setFont(new Font(Font.DIALOG, Font.TRUETYPE_FONT, fontSize));

		TextLayout textLayout = new TextLayout(descricao, g2d.getFont(), g2d.getFontRenderContext());

//		double textHeight = textLayout.getBounds().getHeight();
		double textWidth = textLayout.getBounds().getWidth();

		g2d.drawString(descricao, bi.getWidth() / 2 - (int) textWidth / 2, fontDimension.height);

//		g2d.draw(new Ellipse2D.Double(0, textHeight + sp.intValue(), iconWidth.getHeight(), iconWidth.getHeight()));

		BigDecimal bdy = BigDecimal.valueOf(0);

		if (width != iconWidth.getWidth()) {
			bdy = BigDecimal.valueOf(width - iconWidth.getWidth());
			bdy = bdy.divide(BigDecimal.valueOf(2), 3, BigDecimal.ROUND_CEILING);
		}

		BigDecimal x = BigDecimal.valueOf(fontDimension.getHeight() + sp.intValue());
//		BigDecimal x = BigDecimal.valueOf(0);

//		g2d.draw(new Rectangle2D.Double(bdy.doubleValue(), fontDimension.getHeight() + sp.intValue(),
//				iconWidth.getWidth(), iconWidth.getHeight()));

		printIcons(g2d, gl, iconWidth.height, bw, bdy, x);

		return bi;
	}

//	private static double[] getIconDimensions(String text, int h) {
//	
//	}

	private static Dimension getFontMetrics(GrupoLocais gl, String text, int h) {
		BufferedImage bi = new BufferedImage(h, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();

		BigDecimal s = BigDecimal.valueOf(1.1f);

		g2d.setFont(new Font(Font.DIALOG, Font.TRUETYPE_FONT, h));

		TextLayout textLayout = new TextLayout(text, g2d.getFont(), g2d.getFontRenderContext());

		double textHeight = textLayout.getBounds().getHeight();
		textHeight = BigDecimal.valueOf(textHeight).multiply(BigDecimal.valueOf(1.2f)).doubleValue();

		double textWidth = textLayout.getBounds().getWidth();
		textWidth = BigDecimal.valueOf(textWidth).multiply(s).doubleValue();

		return new Dimension((int) textWidth, (int) textHeight);

	}

	private static final Map<Integer, List<Integer>> mapRiscos = new HashMap<>();

	private static void printIcons(Graphics2D g2d, GrupoLocais gl, int h, BigDecimal w, BigDecimal x, BigDecimal y) {

		BigDecimal sp = w.multiply(BigDecimal.valueOf(0.02));

		for (Local local : gl.getLocais()) {
			for (Risco r : local.getRiscos()) {
				int tm = r.getTamanho();
				if (!mapRiscos.containsKey(tm)) {
					mapRiscos.put(tm, new ArrayList<>());
				}

				int g = r.getGrupoRisco();

				List<Integer> grupos = mapRiscos.get(tm);
				if (!grupos.contains(g)) {
					grupos.add(g);
				}
			}
		}

		BigDecimal ww = BigDecimal.ZERO;
		ww = ww.add(sp);
		ww = ww.add(mapRiscos.containsKey(RISCO_G) ? BigDecimal.valueOf(h).multiply(BigDecimal.valueOf(.9f)).add(sp)
				: BigDecimal.ZERO);
		ww = ww.add(mapRiscos.containsKey(RISCO_M) ? BigDecimal.valueOf(h).multiply(BigDecimal.valueOf(.7f)).add(sp)
				: BigDecimal.ZERO);
		ww = ww.add(mapRiscos.containsKey(RISCO_P) ? BigDecimal.valueOf(h).multiply(BigDecimal.valueOf(.5f)).add(sp)
				: BigDecimal.ZERO);
		ww = ww.add(sp);

		x = w.multiply(BigDecimal.valueOf(.5));
		x = x.subtract(ww.multiply(BigDecimal.valueOf(.5)));
		x = x.add(sp);

		if (mapRiscos.containsKey(RISCO_G)) {
			BigDecimal bdw = BigDecimal.valueOf(h);
			bdw = bdw.multiply(BigDecimal.valueOf(.9f));

			List<Integer> grupos = mapRiscos.get(RISCO_G);

			BigDecimal props = BigDecimal.ONE;
			props = props.divide(BigDecimal.valueOf(grupos.size()), 5, BigDecimal.ROUND_CEILING);

			BigDecimal size = BigDecimal.valueOf(360).multiply(props);

			int arc = 0;

			y = y.add(sp);

			for (Integer v : grupos) {
				g2d.setColor(getColor(v));
				g2d.fillArc(x.intValue(), y.intValue(), bdw.intValue(), bdw.intValue(), arc, size.intValue());
				arc += size.intValue();
			}

			g2d.setColor(Color.DARK_GRAY);
			g2d.draw(new Ellipse2D.Double(x.doubleValue(), y.doubleValue(), bdw.doubleValue(), bdw.doubleValue()));

			x = x.add(bdw).add(sp);
//				g2d.drawOval(cx, y.intValue(), bdw.intValue(), bdw.intValue());
		}

		if (mapRiscos.containsKey(RISCO_M)) {
			BigDecimal bdw = BigDecimal.valueOf(h);
			bdw = bdw.multiply(BigDecimal.valueOf(.7f));

			List<Integer> grupos = mapRiscos.get(RISCO_M);

			BigDecimal cy = BigDecimal.valueOf(h);
			cy = cy.subtract(bdw);
			cy = cy.multiply(BigDecimal.valueOf(.5));
			cy = cy.add(y);

			BigDecimal props = BigDecimal.ONE;
			props = props.divide(BigDecimal.valueOf(grupos.size()), 5, BigDecimal.ROUND_CEILING);

			BigDecimal size = BigDecimal.valueOf(360).multiply(props);

			int arc = 0;

			for (Integer v : grupos) {
				g2d.setColor(getColor(v));
				g2d.fillArc(x.intValue(), cy.intValue(), bdw.intValue(), bdw.intValue(), arc, size.intValue());
				arc += size.intValue();
			}

			g2d.setColor(Color.DARK_GRAY);
			g2d.draw(new Ellipse2D.Double(x.doubleValue(), cy.doubleValue(), bdw.doubleValue(), bdw.doubleValue()));

			x = x.add(bdw).add(sp);
//				g2d.setColor(Color.DARK_GRAY);
//				g2d.draw(new Ellipse2D.Double(x.doubleValue(), cy.doubleValue(),
//						bdw.doubleValue(), bdw.doubleValue()));

//				g2d.setColor(Color.DARK_GRAY);
//				g2d.drawOval(cx, y.intValue(), bdw.intValue(), bdw.intValue());
		}

		if (mapRiscos.containsKey(RISCO_P)) {
			BigDecimal bdw = BigDecimal.valueOf(h);
			bdw = bdw.multiply(BigDecimal.valueOf(.5f));

			List<Integer> grupos = mapRiscos.get(RISCO_P);

			BigDecimal cy = BigDecimal.valueOf(h);
			cy = cy.subtract(bdw);
			cy = cy.multiply(BigDecimal.valueOf(.5));
			cy = cy.add(y);

			BigDecimal props = BigDecimal.ONE;
			props = props.divide(BigDecimal.valueOf(grupos.size()), 5, BigDecimal.ROUND_CEILING);

			BigDecimal size = BigDecimal.valueOf(360).multiply(props);

			int arc = 0;

			for (Integer v : grupos) {
				g2d.setColor(getColor(v));
				g2d.fillArc(x.intValue(), cy.intValue(), bdw.intValue(), bdw.intValue(), arc, size.intValue());
				arc += size.intValue();
			}

			g2d.setColor(Color.DARK_GRAY);
			g2d.draw(new Ellipse2D.Double(x.doubleValue(), cy.doubleValue(), bdw.doubleValue(), bdw.doubleValue()));
		}

		mapRiscos.clear();
	}

	private static Color getColor(int ref) {

		switch (ref) {
		case 2:
			return new Color(0xF40102);
		case 1:
			return new Color(0x70AD48);
		case 4:
			return new Color(0xFFFE01);
		case 3:
			return new Color(0xC45910);
		case 5:
			return new Color(0x2270C0);

		default:
			return null;
		}
	}

	public static Dimension calculeWidth(int h) {
		BigDecimal bh = BigDecimal.valueOf(h);
		BigDecimal bw = BigDecimal.valueOf(h);

		BigDecimal sp = bh.multiply(BigDecimal.valueOf(0.1));

		bw = bw.add(sp);
		bw = bw.add(bh.multiply(BigDecimal.valueOf(0.9)));
		bw = bw.add(sp);
		bw = bw.add(bh.multiply(BigDecimal.valueOf(0.7)));
		bw = bw.add(sp);
		bw = bw.add(bh.multiply(BigDecimal.valueOf(0.5)));
		bw = bw.add(sp);

		return new Dimension(bw.intValue(), h);

	}

	public static Dimension calculeWidth(GrupoLocais gl, int h) {

		BigDecimal rg = BigDecimal.ZERO;
		BigDecimal rm = BigDecimal.ZERO;
		BigDecimal rp = BigDecimal.ZERO;

		long rh = 0l;

		BigDecimal ret = BigDecimal.valueOf(0.1);
		ret = BigDecimal.valueOf(h).multiply(ret);

		for (Local loc : gl.getLocais()) {

			if (loc.getRiscos() != null) {

				for (Risco r : loc.getRiscos()) {

					switch (r.getTamanho()) {

					case RISCO_G:
						if (rg.equals(BigDecimal.ZERO)) {
							BigDecimal w = BigDecimal.valueOf(h);
							w = w.multiply(BigDecimal.valueOf(0.9));
							rg = w;
							rh = rh < rg.longValue() ? rg.longValue() : rh;

						}
						break;
					case RISCO_M:
						if (rm.equals(BigDecimal.ZERO)) {
							BigDecimal w = BigDecimal.valueOf(h);
							w = w.multiply(BigDecimal.valueOf(0.7));
							rm = w;
							rh = rh < rm.longValue() ? rm.longValue() : rh;

						}
						break;
					case RISCO_P:
						if (rp.equals(BigDecimal.ZERO)) {
							BigDecimal w = BigDecimal.valueOf(h);
							w = w.multiply(BigDecimal.valueOf(0.5));
							rp = w;
							rh = rh < rp.longValue() ? rp.longValue() : rh;
						}
						break;
					}
				}
			}
		}

		ret = ret.add(rp).add(rm).add(rg);

		return new Dimension(ret.intValue(), (int) rh);
	}

	public static void applyQualityRenderingHints(Graphics2D g2d) {

		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

	}
}
