package by.bsuir.alekseeva.flowershop.controller.commands.implementations.pages;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.ViewResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.CouponService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CouponsPageCommand implements Command {

    private static final int PAGE_SIZE = 15;

    private final CouponService couponService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int page = RequestUtil.getPageNumber(request);

        Page<Coupon> coupons;
        try {
            coupons = couponService.getCoupons(page, PAGE_SIZE);
        } catch (ServiceException e) {
            log.error("Failed to get coupons", e);
            throw new CommandException("Failed to get coupons", e);
        }

        request.setAttribute("coupons", coupons);
        return new ViewResult("coupons");
    }
}
