# JMeter Performance Tests

## 📦 Structure
- `test-plans/`: Contains .jmx test plans
- `data/`: Test data for parameterized runs
- `scripts/`: Groovy scripts for post-processing
- `config/`: Optional user.properties overrides

## 🛠️ How to Run

```bash
jmeter -n -t jmeter/test-plans/full-user-flow.jmx -l result.jtl -e -o output-folder
