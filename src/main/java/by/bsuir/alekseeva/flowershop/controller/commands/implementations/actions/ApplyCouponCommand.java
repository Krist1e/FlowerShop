package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.StatusCodeResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.CouponService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ApplyCouponCommand implements Command {

    private final CouponService couponService;
    private final ShoppingCartService cartService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String couponString = request.getParameter("coupon");
        if (couponString == null)
            throw new CommandException("Invalid request");
        log.debug("Applying coupon {}", couponString);

        int couponCode;
        try {
            couponCode = Integer.parseInt(couponString);
        } catch (NumberFormatException e) {
            throw new CommandException("Invalid coupon code");
        }
        log.debug("Coupon code is {}", couponCode);

        Optional<Coupon> coupon;
        try {
            coupon = couponService.getCouponByCode(couponCode);
        } catch (ServiceException e) {
            log.error("Failed to get coupon", e);
            throw new CommandException("Failed to get coupon", e);
        }

        if (coupon.isPresent()) {
            int userId = RequestUtil.getUserId(request);
            try {
                cartService.applyCoupon(userId, coupon.get().getId());
            } catch (ServiceException e) {
                log.error("Failed to apply coupon", e);
                throw new CommandException("Failed to apply coupon", e);
            }
            log.debug("Coupon {} applied", couponCode);
            return new StatusCodeResult(200);
        } else {
            log.debug("Coupon {} doesn't exist", couponCode);
            return new StatusCodeResult(400);
        }
    }
}
