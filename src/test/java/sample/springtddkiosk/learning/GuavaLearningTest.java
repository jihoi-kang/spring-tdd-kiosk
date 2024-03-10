package sample.springtddkiosk.learning;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

public class GuavaLearningTest {

    @DisplayName("주어진 개수 만큼 List를 파티셔닝한다")
    @Test
    void test1() {
        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 3);

        // then
        assertThat(partition).hasSize(2)
            .isEqualTo(
                List.of(List.of(1, 2, 3), List.of(4, 5, 6))
            );
    }

    @DisplayName("멀티맵기능 테스트")
    @Test
    void test2() {
        // given
        ArrayListMultimap<@Nullable String, @Nullable String> multiMap = ArrayListMultimap.create();
        multiMap.put("커피", "아메리카노");
        multiMap.put("커피", "카페라떼");
        multiMap.put("커피", "카푸치노");
        multiMap.put("베이커리", "크루아상");
        multiMap.put("베이커리", "식빵");

        // when
        Collection<String> result = multiMap.get("커피");

        // then
        assertThat(result).hasSize(3)
            .isEqualTo(List.of("아메리카노", "카페라떼", "카푸치노"));
    }

    @DisplayName("멀티맵기능 테스트")
    @TestFactory
    Collection<DynamicTest> test3() {
        // given
        ArrayListMultimap<@Nullable String, @Nullable String> multiMap = ArrayListMultimap.create();
        multiMap.put("커피", "아메리카노");
        multiMap.put("커피", "카페라떼");
        multiMap.put("커피", "카푸치노");
        multiMap.put("베이커리", "크루아상");
        multiMap.put("베이커리", "식빵");

        return List.of(
            DynamicTest.dynamicTest("1개 value를 삭제", () -> {
                // given

                // when
                multiMap.remove("커피", "카푸치노");

                // then
                Collection<String> result = multiMap.get("커피");
                assertThat(result).hasSize(2)
                    .isEqualTo(List.of("아메리카노", "카페라떼"));
            }),
            DynamicTest.dynamicTest("1개 key를 삭제", () -> {
                // given

                // when
                multiMap.removeAll("커피");

                // then
                Collection<String> result = multiMap.get("커피");
                assertThat(result).isEmpty();
            })
        );
    }

}
