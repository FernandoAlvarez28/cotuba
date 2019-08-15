package cotuba;

import cotuba.exception.IllegalParameterException;
import cotuba.reader.LeitorOpcoesCLI;

public class Main {

	public static void main(String[] args) {

		final LeitorOpcoesCLI leitorOpcoesCLI;
		try {
			leitorOpcoesCLI = new LeitorOpcoesCLI(args);
		} catch (IllegalParameterException e) {
			System.err.println(e.getMessage());
			e.printOptions();
			System.exit(1);
			return;
		}

		Path diretorioDosMD = leitorOpcoesCLI.getDiretorioDosMD();
		Formato formato = leitorOpcoesCLI.getFormato();
		Path arquivoDeSaida = leitorOpcoesCLI.getArquivoDeSaida();
		boolean modoVerboso = leitorOpcoesCLI.isModoVerboso();

		try {

			if (Formato.PDF.equals(formato)) {
				try(PdfWriter writer = new PdfWriter(Files.newOutputStream(arquivoDeSaida));
					PdfDocument pdf = new PdfDocument(writer);
					Document pdfDocument = new Document(pdf)) {

					PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");
					try (Stream<Path> arquivosMD = Files.list(diretorioDosMD)) {
						arquivosMD
							.filter(matcher::matches)
							.sorted()
							.forEach(arquivoMD -> {
								Parser parser = Parser.builder().build();
								Node document = null;
								try {
									document = parser.parseReader(Files.newBufferedReader(arquivoMD));
									document.accept(new AbstractVisitor() {
										@Override
										public void visit(Heading heading) {
											if (heading.getLevel() == 1) {
												// capítulo
												String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
												// TODO: usar título do capítulo
											} else if (heading.getLevel() == 2) {
												// seção
											} else if (heading.getLevel() == 3) {
												// título
											}
										}

									});
								} catch (Exception ex) {
									throw new RuntimeException("Erro ao fazer parse do arquivo " + arquivoMD, ex);
								}

								try {
									HtmlRenderer renderer = HtmlRenderer.builder().build();
									String html = renderer.render(document);

									List<IElement> convertToElements = HtmlConverter.convertToElements(html);
									for (IElement element : convertToElements) {
										pdfDocument.add((IBlockElement) element);
									}
									// TODO: não adicionar página depois do último capítulo
									pdfDocument.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

								} catch (Exception ex) {
									throw new RuntimeException("Erro ao renderizar para HTML o arquivo " + arquivoMD, ex);
								}

							});
					} catch (IOException ex) {
						throw new RuntimeException(
								"Erro tentando encontrar arquivos .md em " + diretorioDosMD.toAbsolutePath(), ex);
					}

				} catch (Exception ex) {
					throw new RuntimeException("Erro ao criar arquivo PDF: " + arquivoDeSaida.toAbsolutePath(), ex);
				}

			} else if (Formato.EPUB.equals(formato)) {
				Book epub = new Book();

				PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");
				try (Stream<Path> arquivosMD = Files.list(diretorioDosMD)) {
					arquivosMD
						.filter(matcher::matches)
						.sorted()
						.forEach(arquivoMD -> {
							Parser parser = Parser.builder().build();
							Node document = null;
							try {
								document = parser.parseReader(Files.newBufferedReader(arquivoMD));
								document.accept(new AbstractVisitor() {
									@Override
									public void visit(Heading heading) {
										if (heading.getLevel() == 1) {
											// capítulo
											String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
											// TODO: usar título do capítulo
										} else if (heading.getLevel() == 2) {
											// seção
										} else if (heading.getLevel() == 3) {
											// título
										}
									}

								});
							} catch (Exception ex) {
								throw new RuntimeException("Erro ao fazer parse do arquivo " + arquivoMD, ex);
							}

							try {
								HtmlRenderer renderer = HtmlRenderer.builder().build();
								String html = renderer.render(document);

								// TODO: usar título do capítulo
								epub.addSection("Capítulo", new Resource(html.getBytes(), MediatypeService.XHTML));

							} catch (Exception ex) {
								throw new RuntimeException("Erro ao renderizar para HTML o arquivo " + arquivoMD, ex);
							}
						});
				} catch (IOException ex) {
					throw new RuntimeException(
							"Erro tentando encontrar arquivos .md em " + diretorioDosMD.toAbsolutePath(), ex);
				}

				EpubWriter epubWriter = new EpubWriter();

				try {
					epubWriter.write(epub, Files.newOutputStream(arquivoDeSaida));
				} catch (IOException ex) {
					throw new RuntimeException("Erro ao criar arquivo EPUB: " + arquivoDeSaida.toAbsolutePath(), ex);
				}
			} else {
				throw new RuntimeException("Formato do ebook inválido: " + formato);
			}

			System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			if (modoVerboso) {
				ex.printStackTrace();
			}
			System.exit(1);
		}
	}

}
