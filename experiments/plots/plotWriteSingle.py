import matplotlib.pyplot as plt
import numpy as np




# Create the range.
x_axis_label = [1, 2, 4, 8, 16, 32, 64, 128, 256]
y = [85055, 85119, 85247, 85503, 86015, 107141, 129393, 173897, 262841]
y = [number / 10000 for number in y]
# Extrapolate the results

fig = plt.figure(1, figsize=(5, 3.5))
ax = fig.add_subplot()
a = np.arange(len(x_axis_label))

# Add the plots
ax.plot(a, y)

# Set the x axis
ax.xaxis.set_ticks(a)
ax.xaxis.set_ticklabels(x_axis_label)

# Add labels and show the plot
plt.xlabel("Bytes written")
plt.ylabel("Gas cost (x10.000 Gas)")
plt.savefig("writebytes.svg")
plt.show()