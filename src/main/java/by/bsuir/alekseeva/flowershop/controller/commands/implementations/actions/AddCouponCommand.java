package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.CouponService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AddCouponCommand implements Command {

    private final CouponService couponService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int pageNumber = RequestUtil.getPageNumber(request);
        String name = request.getParameter("name");
        String codeString = request.getParameter("code");
        String discountString = request.getParameter("discount");
        if (name == null || codeString == null || discountString == null) {
            throw new CommandException("Missing required parameters");
        }
        log.debug("Adding coupon with name: {}, code: {}, discount: {}", name, codeString, discountString);

        int code = Integer.parseInt(codeString);
        float discount = Float.parseFloat(discountString);

        try {
            couponService.createCoupon(name, code, discount);
        } catch (ServiceException e) {
            log.error("Failed to add coupon", e);
            throw new CommandException("Failed to add coupon", e);
        }

        log.info("Coupon added");
        return new RedirectResult(CommandName.COUPONS_PAGE, pageNumber);
    }
}
