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
public class DeleteCouponCommand implements Command {

    private final CouponService couponService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int pageNumber = RequestUtil.getPageNumber(request);
        String idString = request.getParameter("id");
        if (idString == null) {
            throw new CommandException("Missing required parameter");
        }
        log.debug("Deleting coupon with id: {}", idString);

        int id = Integer.parseInt(idString);

        try {
            couponService.deleteCoupon(id);
        } catch (ServiceException e) {
            log.error("Failed to delete coupon", e);
            throw new CommandException("Failed to delete coupon", e);
        }

        log.info("Coupon {} deleted", id);
        return new RedirectResult(CommandName.COUPONS_PAGE, pageNumber);
    }
}
