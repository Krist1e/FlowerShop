package by.bsuir.alekseeva.flowershop.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private int totalElements;
    private List<T> content;

    public boolean hasNextPage() {
        return pageNumber < totalPages;
    }

    public boolean hasPreviousPage() {
        return pageNumber > 1;
    }

    public static <T> Collector<T, ?, Page<T>> toPage(int pageNumber, int pageSize) {
        return new PageCollector<>(pageNumber, pageSize);
    }

    private static class PageCollector<T> implements Collector<T, List<T>, Page<T>> {
        private final int pageNumber;
        private final int pageSize;
        private int totalPages;
        private int totalElements;
        private List<T> content;

        public PageCollector(int pageNumber, int pageSize) {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }

        @Override
        public Supplier<List<T>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {
            return List::add;
        }

        @Override
        public BinaryOperator<List<T>> combiner() {
            return (list1, list2) -> {
                list1.addAll(list2);
                return list1;
            };
        }

        @Override
        public Function<List<T>, Page<T>> finisher() {
            return list -> {
                totalElements = list.size();
                totalPages = (int) Math.ceil((double) totalElements / pageSize);
                content = list.stream()
                        .skip((long) (pageNumber - 1) * pageSize)
                        .limit(pageSize)
                        .collect(Collectors.toList());
                return new Page<>(pageNumber, pageSize, totalPages, totalElements, content);
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.emptySet();
        }
    }
}
