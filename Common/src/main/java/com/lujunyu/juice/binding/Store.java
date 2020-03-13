package com.lujunyu.juice.binding;

import com.google.inject.Inject;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(onConstructor = @__(@Inject))
public class Store {
  private Set<Fruit<String>> list;
}
