package cipa;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Local;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Risco;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Setor;

@SuppressWarnings("unused")
public class ImgRisco {

//	private static final Color[] colors = { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.ORANGE, Color.PINK,
//			Color.MAGENTA, Color.LIGHT_GRAY, Color.GRAY, Color.BLACK };

	private static final int QUIMICO_VERMELHO = 0xF40102;
	private static final int MECANICO_AZUL = 0x2270C0;
	private static final int BIOLOGICO_MARROM = 0xC45910;
	private static final int ERGONOMICO_AMARELO = 0xFFFE01;
	private static final int FISICO_VERDE = 0x70AD48;
	private static final int RISCO_P = 1;
	private static final int RISCO_M = 2;
	private static final int RISCO_G = 3;

	private static final Map<Integer, List<Integer>> mapRiscos = new HashMap<>();

//	private static final int[] icolors = { 0xDB0505, 0x1E66B0, 0x9A2F29, 0xFEFA01, 0x38B145 };

//	private static final Color background = Color.DARK_GRAY;

	private static final Map<String, Image> cache = new HashMap<>();

	public static BufferedImage getIconRisco(List<Setor> setores, Long id, int h) {

		mapRiscos.clear();

		BufferedImage imageBuffer = null;

		for (Setor setor : setores) {
			List<Local> locais = setor.getLocais();

			for (Local local : locais) {

				if (id.equals(local.getId())) {

					imageBuffer = createImgRisco(local.getRiscos(), h);

					break;
				}
			}

		}

		return imageBuffer;
	}

	private static void createIconRisco(Graphics2D g2, int keyIcon, BigDecimal x, BigDecimal w) {

//		System.out.println(mapRiscos.containsKey(keyIcon));

//		if(mapRiscos.containsKey(keyIcon)) {
		Color color = Color.BLUE;
		g2.fill(new Ellipse2D.Double(0, 0, w.intValue(), w.intValue()));
		x = x.add(w);

		System.out.println(x);

//		}
	}

	private static BigDecimal calculeWidth(Set<Integer> tms, int h) {
		BigDecimal w = BigDecimal.ZERO;
		for (Integer t : tms) {

			BigDecimal w2 = BigDecimal.ZERO;
			BigDecimal prop;

			switch (t) {
			case RISCO_P:
				prop = BigDecimal.ONE.divide(new BigDecimal(3), 5, BigDecimal.ROUND_CEILING);
				w2 = new BigDecimal(h).multiply(prop);
				break;
			case RISCO_M:
				prop = new BigDecimal(2).divide(new BigDecimal(3), 5, BigDecimal.ROUND_CEILING);
				w2 = new BigDecimal(h).multiply(prop);
				break;
			case RISCO_G:
				prop = BigDecimal.ONE;
				w2 = new BigDecimal(h).multiply(prop);
				break;
			}

			w = w.add(w2);
		}

		return w;
	}

	private static void prepareRiscos(List<Risco> riscos) {
		for (Risco risco : riscos) {

			int keyTamanho = risco.getTamanho();

			if (!mapRiscos.containsKey(keyTamanho)) {
				mapRiscos.put(keyTamanho, new ArrayList<>());
			}

			List<Integer> grupos = mapRiscos.get(keyTamanho);
			int g = risco.getGrupoRisco();

			if (!grupos.contains(g)) {
				grupos.add(g);
			}
		}
	}

	public static BufferedImage getRiscoLinha(int grupoRisco, int tamanho, int w, int h) {

		String srisco = String.format("%d-%d", grupoRisco, tamanho);

		if (cache.containsKey(srisco)) {
			return imageToBufferedImage(cache.get(srisco));
		}

		BufferedImage imageBuffer = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics2D g2 = imageBuffer.createGraphics();

//		applyQualityRenderingHints(g2);

		BigDecimal prop = new BigDecimal(tamanho).divide(new BigDecimal(3), 5, BigDecimal.ROUND_CEILING);

		BigDecimal tm = new BigDecimal(w).multiply(prop);

		g2.setColor(getColor(grupoRisco));
		g2.fill(new Rectangle2D.Double(0, 0, tm.doubleValue(), h));

//		int color = imageBuffer.getRGB(0, 0);
//		Image img = makeColorTransparent(imageBuffer, new Color(color), 0.01f);

//		cache.put(srisco, img);

		return imageBuffer;

	}

	private static int getSize(String ref) {
		switch (ref) {
		case "P":
			return 1;
		case "M":
			return 2;
		case "G":
			return 3;
		}
		return -1;
	}

	private static Color getColor(int ref) {

		switch (ref) {
		case 2:
			return new Color(QUIMICO_VERMELHO);
		case 1:
			return new Color(FISICO_VERDE);
		case 4:
			return new Color(ERGONOMICO_AMARELO);
		case 3:
			return new Color(BIOLOGICO_MARROM);
		case 5:
			return new Color(MECANICO_AZUL);

		default:
			return null;
		}
	}

	public static BufferedImage getRisco(int risco, int isize) {
		switch (risco) {
		case 1:
			return getIcon(new int[] { ERGONOMICO_AMARELO }, isize);
		case 2:
			return getIcon(new int[] { QUIMICO_VERMELHO, ERGONOMICO_AMARELO }, isize);
		case 3:
			return getIcon(new int[] { BIOLOGICO_MARROM, FISICO_VERDE, MECANICO_AZUL }, isize);

		default:
			return getIcon(new int[] { ERGONOMICO_AMARELO }, isize);
		}
	}

	public static BufferedImage getLogo2(int isize) {
		int d = (int) (isize * 0.38);
		double sa = (d * 0.33);
		double sb = (d * 0.6666);

		int t = (int) ((d * 2) + (sa * 2));

		Color cR = new Color(0xF40102);
		Color cY = new Color(0xFFFE01);
		Color cB = new Color(0x0910FE);

		BufferedImage imageBuffer = new BufferedImage(t, t, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics2D g2 = imageBuffer.createGraphics();

		applyQualityRenderingHints(g2);

		g2.setColor(cR);
		g2.fill(new Rectangle2D.Double(0, sb, d, d));
		g2.draw(new Rectangle2D.Double(sb, 0, d, d));

		g2.setColor(cY);
		g2.draw(new Ellipse2D.Double(sa, sa, d, d));

		int color = imageBuffer.getRGB(0, 0);
		Image img = makeColorTransparent(imageBuffer, new Color(color), 0.01f);

		return imageToBufferedImage(img);
	}

	public static BufferedImage getLogo(int isize) {

		int d = (int) (isize * 0.3846153846);
		int d2 = d / 2;
		double sa = (d * 0.3333);
		double sb = (d * 0.6666);
		double sp = (d * 0.0);

		int t = (int) ((d * 2) + (sa * 2));

		BufferedImage imageBuffer = new BufferedImage(t, t, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics2D g2 = imageBuffer.createGraphics();

		applyQualityRenderingHints(g2);

		Color cR = new Color(0xF40102);
		Color cG = new Color(0x55, 0xFE, 0x00);
		Color cB = new Color(0x0910FE);
		Color cW = new Color(0xFF);
		Color cY = new Color(0xFFFE01);

		g2.setColor(cR);
		g2.fill(new Rectangle2D.Double(d2 + (sa / 2) + sp, d + sa, d, d));

		g2.setColor(cB);
		g2.fill(new Rectangle2D.Double(0, sb + sp, d, d));

		g2.setColor(cG);
		g2.fill(new Rectangle2D.Double(sb - sp, 0, d, d));

		g2.setColor(cR);
		g2.draw(new Rectangle2D.Double(d + sa, sb - sp, d, d));

		g2.setColor(cY);
		g2.drawOval(d, d, d, d);

		int color = imageBuffer.getRGB(0, 0);
		Image img = makeColorTransparent(imageBuffer, new Color(color), 0.01f);

		return imageToBufferedImage(img);
	}

	public static BufferedImage getIcon(int[] riscos, int size) {
		int d = size - 6;

		Color cB = new Color(0x929292);
		Color cE = new Color(ERGONOMICO_AMARELO);

		BufferedImage imageBuffer = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics2D g = imageBuffer.createGraphics();

		applyQualityRenderingHints(g);

		int arc = 0;

		int[] sizes = calculateAngles(riscos);

		for (int i = 0, j = 0; i < sizes.length; i++, j++) {
			j = j == 10 ? 0 : j;
			g.setColor(new Color(riscos[j]));
			g.fillArc(2, 2, d, d, arc, sizes[i]);

			arc += sizes[j];
		}

		g.setStroke(new BasicStroke(1));

		int color = imageBuffer.getRGB(0, 0);
		Image img = makeColorTransparent(imageBuffer, new Color(color), 0.01f);

		g.setColor(Color.DARK_GRAY);
		g.draw(new Ellipse2D.Double(2, 2, d, d));

		return imageToBufferedImage(img);

	}

	public static BufferedImage getIcon(int i) {
		switch (i) {
		case 1:
			return getIcon(new int[] { ERGONOMICO_AMARELO });
		case 2:
			return getIcon(new int[] { QUIMICO_VERMELHO, ERGONOMICO_AMARELO });
		case 3:
			return getIcon(new int[] { BIOLOGICO_MARROM, FISICO_VERDE, MECANICO_AZUL });

		default:
			return getIcon(new int[] { ERGONOMICO_AMARELO });
		}
	}

	public static BufferedImage getIcon(int[] riscos) {
		int diameter = 200;

		BufferedImage imageBuffer = new BufferedImage(diameter + 2, diameter + 2, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics2D g = imageBuffer.createGraphics();

//		g.setColor(color);
		g.fillRect(0, 0, diameter, diameter);

		int arc = 0;

		int[] sizes = calculateAngles(riscos);

		applyQualityRenderingHints(g);

		for (int i = 0, j = 0; i < sizes.length; i++, j++) {
			j = j == 10 ? 0 : j;
			g.setColor(new Color(riscos[j]));
			g.fillArc(1, 1, diameter, diameter, arc, sizes[i]);

			arc += sizes[j];
		}

		int color = imageBuffer.getRGB(0, 0);
		Image img = makeColorTransparent(imageBuffer, new Color(color), 0.01f);

		g.setColor(new Color(MECANICO_AZUL));
		g.drawOval(0, 0, diameter + 2, diameter + 2);

		return imageToBufferedImage(img);
//		return imageBuffer;
	}

	public static BufferedImage getIcon() {

		int diameter = 40;

		BufferedImage imageBuffer = new BufferedImage(diameter, diameter, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics2D g = imageBuffer.createGraphics();

//		g.setColor(color);
		g.fillRect(0, 0, diameter, diameter);

		int arc = 0;

//		int[] riscos = { QUIMICO_VERMELHO, ERGONOMICO_AMARELO, FISICO_VERDE, BIOLOGICO_MARROM};
		int[] riscos = { BIOLOGICO_MARROM, FISICO_VERDE, ERGONOMICO_AMARELO };

		int[] sizes = calculateAngles(riscos);

		applyQualityRenderingHints(g);

		for (int i = 0, j = 0; i < sizes.length; i++, j++) {
			j = j == 10 ? 0 : j;
			g.setColor(new Color(riscos[j]));
			g.fillArc(0, 0, diameter, diameter, arc, sizes[i]);

			arc += sizes[j];
		}

		int color = imageBuffer.getRGB(0, 0);
		Image img = makeColorTransparent(imageBuffer, new Color(color), 0.01f);

		return imageToBufferedImage(img);
//		return imageBuffer;

	}

	private static BufferedImage imageToBufferedImage(Image image) {

		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_4BYTE_ABGR_PRE);

		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return bufferedImage;

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

	/**
	 * 58 * Calcula os ângulos para cada valor informado. 59 * @param values Valores
	 * a terem seus ângulos calculados. 60 * @return Array de int com os ângulos
	 * para cada valor. 61
	 */
	private static int[] calculateAngles(int[] values) {
		int[] angles = new int[values.length];
		int total = 0;
		// calcula a somatória total dos valores
		for (int i = 0; i < values.length; i++) {
//			total += values[i];
			total += 1;
		}
		// calcula os ângulos para cada pedaço
		for (int i = 0; i < values.length; i++) {
			angles[i] = (360 * 1) / total;
		}
		return angles;
	}

	public static BufferedImage getIcon(Long localId) {
		BufferedImage img = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);

//		img.setRGB(0, 90, 100);

		int diameter = Math.min(img.getWidth(), img.getHeight());

		BufferedImage mask = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = mask.createGraphics();
		applyQualityRenderingHints(g2d);

		g2d.fillOval(0, 0, diameter - 2, diameter - 2);
		g2d.dispose();

		BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
		g2d = masked.createGraphics();
		g2d.setColor(Color.CYAN);
		applyQualityRenderingHints(g2d);
//	    int x = (50) / 2;
//	    int y = (50) / 2;

		int x = (diameter - img.getWidth()) / 2;
		int y = (diameter - img.getHeight()) / 2;

		g2d.drawImage(img, x, y, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
		g2d.drawImage(mask, 0, 0, null);
		g2d.dispose();

		return masked;
	}

	public static void main(String[] args) throws IOException {

		File outputfile = new File("image.png");

//		BufferedImage img = new BufferedImage( 
//                500, 500, BufferedImage.TYPE_INT_RGB );

//		int r = 5;
//        int g = 25;
//        int b = 255;
//        int col = (r << 16) | (g << 8) | b;
//        for(int x = 0; x < 500; x++){
//            for(int y = 20; y < 300; y++){
//                img.setRGB(x, y, col);
//            }
//        }

		Local local = new Local();
		local.setId(1L);
		local.setDescricao("Escritório Administrativo Operacional");

		List<Risco> riscos = new ArrayList<>();

		Risco risco = new Risco();
		risco.setTamanho(RISCO_G);
		riscos.add(risco);

		risco = new Risco();
		risco.setTamanho(RISCO_P);
		riscos.add(risco);

		risco = new Risco();
		risco.setTamanho(RISCO_M);
		riscos.add(risco);

		local.setRiscos(riscos);

//		BufferedImage img = createImgRisco2(riscos, 50, 12);
//		BufferedImage img = createImgRiscoFinal(local, 300);

//		BufferedImage img = getRiscoLinha(1, 3, 50, 6);
		BufferedImage img = createImgRisco2(riscos, 50, 12);

		ImageIO.write(img, "png", outputfile);

		System.out.println(outputfile.getAbsolutePath());
	}

	private static BufferedImage createImgRiscoFinal(Local local, int h) {
		String descricao = String.format("%02d - %S", local.getId(), local.getDescricao().toUpperCase());

		BigDecimal bw = BigDecimal.valueOf(500);
		BigDecimal bh = BigDecimal.valueOf(20);

		BufferedImage mask = new BufferedImage(bw.intValue(), h, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = mask.createGraphics();
		applyQualityRenderingHints(g2d);

		g2d.setColor(Color.BLACK);

//		g2d.draw(new Rectangle2D.Double(0, 0, mask.getWidth(), mask.getHeight()));

		g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));

		TextLayout textLayout = new TextLayout(descricao, g2d.getFont(), g2d.getFontRenderContext());

		double textHeight = textLayout.getBounds().getHeight();
		double textWidth = textLayout.getBounds().getWidth();

		drawStringMultiLine(g2d, descricao, h, 0, 0);

//		g2d.drawString(descricao, mask.getWidth() / 2 - (int) textWidth / 2,
//                mask.getHeight() / 2 + (int) textHeight / 2);

		return mask;
	}

	private static String formatText(Graphics2D g, String text, int lineWidth) {
		FontMetrics m = g.getFontMetrics();
		if (m.stringWidth(text) < lineWidth) {
			return text;
		} else {
			String[] words = text.split(" ");
			String currentLine = words[0];

			StringBuilder sb = new StringBuilder();

			sb.append(currentLine);

			for (int i = 1; i < words.length; i++) {
				if (m.stringWidth(currentLine + words[i]) < lineWidth) {
					sb.append(" ").append(words[i]);
				} else {
					sb.append(words[i]).append("\n");
//					g.drawString(currentLine, x, y);
//					y += m.getHeight();
					currentLine = words[i];
				}
			}
//			if (currentLine.trim().length() > 0) {
//				sb.append(currentLine);
//			}

			return sb.toString();
		}
	}

	public static void drawStringMultiLine(Graphics2D g, String text, int lineWidth, int x, int y) {
		FontMetrics m = g.getFontMetrics();
		if (m.stringWidth(text) < lineWidth) {
			g.drawString(text, x, y);
		} else {
			String[] words = text.split(" ");
			String currentLine = words[0];
			for (int i = 1; i < words.length; i++) {
				if (m.stringWidth(currentLine + words[i]) < lineWidth) {
					currentLine += " " + words[i];
				} else {
					g.drawString(currentLine, x, y);
					y += m.getHeight();
					currentLine = words[i];
				}
			}
			if (currentLine.trim().length() > 0) {
				g.drawString(currentLine, x, y);
			}
		}
	}

	public static BufferedImage createImgRisco2(List<Risco> riscos, int h, int fontSize) {
		for (Risco r : riscos) {
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

		BigDecimal bh = BigDecimal.valueOf(h);
		BigDecimal sp = bh.multiply(BigDecimal.valueOf(0.1));

		BigDecimal wtotal = BigDecimal.valueOf(h);

		wtotal = wtotal.add(sp);

		wtotal = wtotal.add(bh.multiply(BigDecimal.valueOf(0.9)));
		wtotal = wtotal.add(sp);
//		
		wtotal = wtotal.add(bh.multiply(BigDecimal.valueOf(0.7)));
		wtotal = wtotal.add(sp);
//		
		wtotal = wtotal.add(bh.multiply(BigDecimal.valueOf(0.5)));
		wtotal = wtotal.add(sp);

		BufferedImage mask = new BufferedImage(wtotal.intValue(), h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = mask.createGraphics();
		applyQualityRenderingHints(g2d);

//		g2d.setColor(Color.CYAN);
//		g2d.fillRect(0, 0, wtotal.intValue(), h);

		g2d.setColor(Color.DARK_GRAY);
		g2d.setFont(new Font(Font.DIALOG, Font.BOLD, fontSize));

		String text = "001 - Texto Longo de Teste para confundir ainda mais o povo";

		List<String> frases = getFrases(g2d, text, wtotal.intValue());

		double fsp = fontSize * 0.15;
		fsp = fsp * (frases.size() - 1);
		

		Dimension fontDimension = new Dimension(wtotal.intValue(), (int) (fontSize * frases.size() + fsp));
		
//		Dimension iconsDimension = new Dimension(wtotal.intValue(), height);
		

//		AttributedString styledText = new AttributedString(text);

//		FontRenderContext frc = g.getFontRenderContext();
//		TextLayout layout = new TextLayout(text, font, frc);

//		FontRenderContext frc = gzAA.getFontRenderContext();
//		TextLayout layout = new TextLayout(text, font, frc);

//		drawStringMultiLine(g2d, text , wtotal.intValue(), 0, 15);

		return mask;
	}

	private static List<String> getFrases(Graphics2D g, String text, int width) {
		List<String> ret = new ArrayList<>();
		FontMetrics m = g.getFontMetrics();
		Iterator<String> words = Arrays.asList(text.split(" ")).iterator();

		StringBuilder sb = new StringBuilder();

		while (words.hasNext()) {
			String w = (String) words.next();
			if (m.stringWidth(sb.toString()) >= width) {
				ret.add(sb.toString().trim());
				sb = new StringBuilder();
			}

			sb.append(w.trim()).append(words.hasNext() ? " " : "");
		}
		ret.add(sb.toString());

		return ret;
	}

	public static BufferedImage createImgRisco(List<Risco> riscos, int h) {

		for (Risco r : riscos) {
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

		BigDecimal w = calculewidth(h);

		int fontSize = BigDecimal.valueOf(h).multiply(BigDecimal.valueOf(0.4f)).intValue();

		BufferedImage mask = new BufferedImage(w.intValue(), h + fontSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = mask.createGraphics();
		applyQualityRenderingHints(g2d);

		g2d.setColor(Color.CYAN);
		g2d.draw(new Rectangle2D.Double(0, 0, mask.getWidth(), mask.getHeight()));

		BigDecimal sw = BigDecimal.valueOf(h);
		BigDecimal x = BigDecimal.ONE;

		BigDecimal bh = BigDecimal.valueOf(h);
		BigDecimal sc = BigDecimal.ZERO;

		BigDecimal dois = BigDecimal.valueOf(2);
		BigDecimal dv = bh.divide(BigDecimal.valueOf(2), 5, BigDecimal.ROUND_CEILING);

		BigDecimal sp = (BigDecimal.valueOf(h).subtract(bh.multiply(BigDecimal.valueOf(.9f)))).divide(dois, 3,
				BigDecimal.ROUND_CEILING);

		if (mapRiscos.containsKey(RISCO_G)) {
			sc = BigDecimal.valueOf(.9f);
			sw = bh.multiply(sc);
			g2d.setColor(Color.DARK_GRAY);

			int cx = x.add(sp).intValue();
			int cy = (bh.subtract(sw)).divide(dois).intValue();

			int ww = sw.intValue();

			List<Integer> grupos = mapRiscos.get(RISCO_G);

			BigDecimal props = BigDecimal.ONE;
			props = props.divide(BigDecimal.valueOf(grupos.size()), 5, BigDecimal.ROUND_CEILING);

			BigDecimal size = BigDecimal.valueOf(360).multiply(props);

			int arc = 0;

			for (Integer v : grupos) {
				g2d.setColor(getColor(v));
				g2d.fillArc(cx, cy, ww, ww, arc, size.intValue());
				arc += size.intValue();
			}

			g2d.setColor(Color.DARK_GRAY);
			g2d.drawOval(cx, cy, ww, ww);

			x = x.add(sw).add(sp);
		}

		if (mapRiscos.containsKey(RISCO_M)) {
			sc = BigDecimal.valueOf(.7f);
			sw = bh.multiply(sc);
			g2d.setColor(Color.DARK_GRAY);

			int cx = x.add(sp).intValue();
			int cy = (bh.subtract(sw)).divide(dois).intValue();

			int ww = sw.intValue();

			List<Integer> grupos = mapRiscos.get(RISCO_M);

			BigDecimal props = BigDecimal.ONE;
			props = props.divide(BigDecimal.valueOf(grupos.size()), 5, BigDecimal.ROUND_CEILING);

			BigDecimal size = BigDecimal.valueOf(360).multiply(props);

			int arc = 0;

			for (Integer v : grupos) {
				g2d.setColor(getColor(v));
				g2d.fillArc(cx, cy, ww, ww, arc, size.intValue());
				arc += size.intValue();
			}

			g2d.setColor(Color.DARK_GRAY);
			g2d.drawOval(cx, cy, ww, ww);

//			x = x.add(sw).add(sp);

//			g2d.fill(new Ellipse2D.Double(cx, cy, ww, ww));
			x = x.add(sw).add(sp);
		}

		if (mapRiscos.containsKey(RISCO_P)) {

			sc = BigDecimal.valueOf(.5f);
			sw = bh.multiply(sc);

			int cx = x.add(sp).intValue();
			int cy = (bh.subtract(sw)).divide(dois).intValue();

			int ww = sw.intValue();

			List<Integer> grupos = mapRiscos.get(RISCO_P);

			BigDecimal props = BigDecimal.ONE;
			props = props.divide(BigDecimal.valueOf(grupos.size()), 5, BigDecimal.ROUND_CEILING);

			BigDecimal size = BigDecimal.valueOf(360).multiply(props);

			int arc = 0;

			for (Integer v : grupos) {
				g2d.setColor(getColor(v));
				g2d.fillArc(cx, cy, ww, ww, arc, size.intValue());
				arc += size.intValue();
			}

			g2d.setColor(Color.DARK_GRAY);
			g2d.drawOval(cx, cy, ww, ww);

//			x = x.add(sw).add(sp);
		}

//		mapRiscos.forEach((k, v) -> {
//			g2d.setColor(Color.GREEN);
//			g2d.fill(new Ellipse2D.Double(x.intValue(), 0, sw.intValue(), sw.intValue()));
//			x.add(sw);
//		});

		return mask;

	}

	private static BigDecimal calculewidth(int h) {
		BigDecimal w = BigDecimal.ZERO;
		BigDecimal bh = BigDecimal.valueOf(h);
		BigDecimal dois = BigDecimal.valueOf(2);
		BigDecimal tres = BigDecimal.valueOf(3);
		BigDecimal sc = BigDecimal.ZERO;

		BigDecimal sp = (bh.subtract(bh.multiply(BigDecimal.valueOf(.9))));

		w = w.add(sp);

		if (mapRiscos.containsKey(RISCO_G)) {
			w = w.add(bh.multiply(BigDecimal.valueOf(.9))).add(sp);
		}

		if (mapRiscos.containsKey(RISCO_M)) {
			w = w.add(bh.multiply(BigDecimal.valueOf(.7))).add(sp);
		}

		if (mapRiscos.containsKey(RISCO_P)) {
			w = w.add(bh.multiply(BigDecimal.valueOf(.5))).add(sp);
		}

		return w;
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
