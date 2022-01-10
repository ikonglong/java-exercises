package guice;

import com.google.inject.*;

public class Application {
  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new DefaultModule());
    BillingService billingService = injector.getInstance(BillingService.class);
    billingService.chargeOrder("book-order-1", "40001600");

    System.out.println("--------------------");

    OrderService orderService = injector.getInstance(OrderService.class);
    orderService.placeOrder("book-order-2", "40001600");

    System.out.println("--------------------");

    System.out.println(
        "billingService == orderService.billingService: "
            + (billingService == orderService.billingService));
    System.out.println(
        "billingService.processor == orderService.billingService.processor:"
            + (billingService.processor == orderService.billingService.processor));
  }

  static class DefaultModule extends AbstractModule {
    @Provides
    @javax.inject.Singleton
    public CreditCardProcessor creditCardProcessor() {
      return new CreditCardProcessor("Visa");
    }

    @Provides
    public ShippingCostCalc shippingCostCalc() {
      return new ShippingCostCalc("顺丰");
    }

    @Provides
    public MarketingService marketingService(ShippingCostCalc shippingCostCalc) {
      return new MarketingService(shippingCostCalc);
    }
  }

  static class OrderService {
    final BillingService billingService;

    @Inject
    public OrderService(BillingService billingService) {
      this.billingService = billingService;
    }

    public void placeOrder(String order, String creditCard) {
      billingService.chargeOrder(order, creditCard);
    }
  }

  @javax.inject.Singleton
  static class BillingService {
    final CreditCardProcessor processor;
    final MarketingService marketingService;

    @Inject
    public BillingService(CreditCardProcessor processor, MarketingService marketingService) {
      System.out.println("Construct a BillingService object");
      this.processor = processor;
      this.marketingService = marketingService;
    }

    public void chargeOrder(String order, String creditCard) {
      System.out.println(
          "[BillingService] order:"
              + order
              + ", creditCard:"
              + creditCard
              + ", processor:"
              + processor);
      marketingService.calcDiscount();
    }
  }

  static class CreditCardProcessor {
    final String name;

    public CreditCardProcessor(String name) {
      this.name = name;
      System.out.println("Construct a CreditCardProcessor object");
    }

    @Override
    public String toString() {
      return String.format("CreditCardProcessor[name=%s]", name);
    }
  }

  static class MarketingService {

    final ShippingCostCalc shippingCostCalc;

    public MarketingService(ShippingCostCalc shippingCostCalc) {
      this.shippingCostCalc = shippingCostCalc;
    }

    public void calcDiscount() {
      System.out.println("免去[" + shippingCostCalc.serviceProvider + "]运费");
    }
  }

  static class ShippingCostCalc {
    final String serviceProvider;

    public ShippingCostCalc(String serviceProvider) {
      this.serviceProvider = serviceProvider;
    }
  }
}
