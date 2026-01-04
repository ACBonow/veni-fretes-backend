package com.venifretes.util;

/**
 * @deprecated Esta classe foi substituída pelo sistema de configurações dinâmicas.
 * Use {@link ConfigHelper} para acessar configurações editáveis pelo administrador.
 *
 * As constantes abaixo são mantidas apenas para compatibilidade retroativa,
 * mas seus valores não são mais usados pelo sistema.
 *
 * Para usar as configurações dinâmicas:
 * @Autowired private ConfigHelper configHelper;
 * int periodoTeste = configHelper.getPeriodoTesteDias();
 */
@Deprecated
public class Constants {
    /**
     * @deprecated Use {@link ConfigHelper#getPeriodoTesteDias()}
     */
    @Deprecated
    public static final int PERIODO_TESTE_DIAS = 15;

    /**
     * @deprecated Use {@link ConfigHelper#getMinFotosVeiculo()}
     */
    @Deprecated
    public static final int MIN_FOTOS_VEICULO = 3;

    /**
     * @deprecated Use {@link ConfigHelper#getMinDescricaoLength()}
     */
    @Deprecated
    public static final int MIN_DESCRICAO_LENGTH = 50;

    /**
     * @deprecated Use {@link ConfigHelper#getMaxFileSizeMB()}
     */
    @Deprecated
    public static final int MAX_FILE_SIZE_MB = 10;
}
