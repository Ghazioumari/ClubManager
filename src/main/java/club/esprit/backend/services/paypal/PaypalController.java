package club.esprit.backend.services.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/paypal")
public class PaypalController {
    private final PaypalService paypalService;
    @GetMapping("/")
    public String home(){
        return "index";
    }
    @PostMapping("/payment/create")
    public RedirectView createPayment(){
        try {
            String cancelUrl = "http://localhost:8080/paypal/payment/cancel";
            String successUrl = "http://localhost:8080/paypal/payment/success";
            Payment payment = paypalService.createPayment(
                    10.0,
                    "USD",
                    "paypal",
                    "sale",
                    "payment description",
                    cancelUrl,
                    successUrl
            );
            for (Links links: payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error("error occured :",e);
        }
        return new RedirectView("/paypal/payment/error");

    }
    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "paymentSuccess";
            }

        } catch(PayPalRESTException e){
            log.error("error occured :",e);
        }
        return "paymentSuccess";
    }
    @GetMapping("/payment/cancel")
    public String paymentCancel(){
        return "paymentCancel";
    }
    @GetMapping("/payment/error")
    public String paymentError(){
        return "paymentCancel";
    }

}
