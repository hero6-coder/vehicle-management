package blueship.vehicle.configs;

import blueship.vehicle.common.Constants;
import blueship.vehicle.exception.VmRestTemplateErrorHandler;
import blueship.vehicle.exception.VmRestTemplateInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.sql.DataSource;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
public class SystemConfigs {
  public final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${api.key}")
  private String apiKey;

  @Bean
  public RestTemplate getResttemplate() {
    RestTemplate restClient = new RestTemplate();
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

    messageConverters.add(new FormHttpMessageConverter());
    messageConverters.add(new StringHttpMessageConverter());

    // Add the Jackson Message converter
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    // Note: here we are making this converter to process any kind of response,
    // not only application/*json, which is the default behaviour
    MediaType[] mt = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8,
      MediaType.APPLICATION_OCTET_STREAM};
    converter.setSupportedMediaTypes(Arrays.asList(mt));
    messageConverters.add(converter);

    restClient.setMessageConverters(messageConverters);
    restClient.setErrorHandler(new VmRestTemplateErrorHandler(getObjectMapper()));

    List<ClientHttpRequestInterceptor> interceptors = restClient.getInterceptors();
    interceptors.add(new VmRestTemplateInterceptor(apiKey));
    restClient.setInterceptors(interceptors);
    return restClient;
  }

  @Bean
  public WebClient.Builder getWebClient() {
    WebClient.Builder webClientBuilder = WebClient.builder();
    ReactorClientHttpConnector connector = new ReactorClientHttpConnector();
    webClientBuilder.clientConnector(connector);
    return webClientBuilder;
  }

  @Bean(name = "jacksonObjectMapper")
  public ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.setDateFormat(new StdDateFormat());

    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper;
  }

  /**
   * Default DateFormat implementation used by standard Dateserializers and
   * deserializers. For serialization defaults to usingan ISO-8601 compliant
   * format (format String "yyyy-MM-dd'T'HH:mm:ss.SSSZ")and for deserialization,
   * both ISO-8601 and RFC-1123.
   *
   * @return
   */
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    return builder -> builder.dateFormat(new StdDateFormat());
  }

  @Bean
  public ReloadableResourceBundleMessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    Locale.setDefault(new Locale("vi", "VN"));
    messageSource.setBasename("classpath:messages");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setCacheSeconds(3600); // Refresh cache once per hour.
    return messageSource;
  }

  @Bean
  public LocalValidatorFactoryBean getValidator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }

  @Bean
  public Locale defaultLocale() {
    Locale bean = new Locale("vi", "VN");
    return bean;
  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
    return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());

  }

  @Bean
  public DataSource dataSource(DataSourceProperties dataSourceProperties) {
    return dataSourceProperties.initializeDataSourceBuilder().build();
  }

  @Bean
  public FormattingConversionService conversionService() {
    DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

    DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
    registrar.setDateFormatter(DateTimeFormatter.ofPattern(Constants.DEFAULT_SHORT_TIME_PATTERN));
    registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(Constants.DEFAULT_FULL_TIME_PATTERN));
    registrar.registerFormatters(conversionService);

    // other desired formatters

    return conversionService;
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(11);
  }
}
